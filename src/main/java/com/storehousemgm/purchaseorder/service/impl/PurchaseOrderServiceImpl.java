package com.storehousemgm.purchaseorder.service.impl;

import com.storehousemgm.exception.InventoryNotExistException;
import com.storehousemgm.exception.PurchaseOrderNotCompletedException;
import com.storehousemgm.exception.PurchaseOrderNotExistException;
import com.storehousemgm.exception.StockNotExistException;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.inventory.repository.InventoryRepository;
import com.storehousemgm.purchaseorder.dto.OrderRequestDto;
import com.storehousemgm.purchaseorder.dto.OrderResponseDto;
import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import com.storehousemgm.purchaseorder.mapper.PurchaseOrderMapper;
import com.storehousemgm.purchaseorder.repository.PurchaseOrderRepository;
import com.storehousemgm.purchaseorder.service.PurchaseOrderService;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.repository.StockRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private StockRepository stockRepository;
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<OrderResponseDto>> findPurchaseOrder(Long orderId) {
        return purchaseOrderRepository.findById(orderId).map(purchaseOrder -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<OrderResponseDto>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("PurchaseOrder Founded")
                    .setData(purchaseOrderMapper.mapPurchaseOrderToOrderResponse(purchaseOrder)));
        }).orElseThrow(() -> new PurchaseOrderNotExistException("OrderId : " + orderId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findPurchaseOrders(Long customerId) {
        List<OrderResponseDto> listPurchaseOrders = purchaseOrderRepository
                .findByCustomerId(customerId)
                .stream()
                .map(purchaseOrder -> purchaseOrderMapper.mapPurchaseOrderToOrderResponse(purchaseOrder))
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<OrderResponseDto>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("PurchaseOrders are Founded")
                .setData(listPurchaseOrders));
    }

    @Override
    public ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            OrderRequestDto orderRequestDto, Long inventoryId) {
        Inventory inventory = inventoryRepository
                .findById(inventoryId)
                .orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
        Stock stock = stockRepository
                .findByInventory(inventory)
                .orElseThrow(() -> new StockNotExistException("Stock not exist...!!!"));

        PurchaseOrder purchaseOrder = null;
        if (stock.getQuantity() >= orderRequestDto.getTotalQuantity()) {

            stock.setQuantity(stock.getQuantity() - orderRequestDto.getTotalQuantity());
            stockRepository.save(stock);

            purchaseOrder = PurchaseOrder.builder()
                    .orderId(orderRequestDto.getOrderId())
                    .invoiceLink(UUID.randomUUID().toString().concat(".jpg"))
                    .orderQuantity(orderRequestDto.getTotalQuantity())
                    .customerId(orderRequestDto.getCustomerId())
                    .invoiceDate(LocalDate.now())
                    .inventories(List.of(inventory))
                    .build();
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        } else {
            throw new PurchaseOrderNotCompletedException("Insufficient Order quantities");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<OrderResponseDto>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("PurchaseOrder Created")
                .setData(OrderResponseDto.builder()
                        .orderId(purchaseOrder.getOrderId())
                        .inventoryTitle(inventory.getProductTitle())
                        .inventoryImage(inventory.getProductImage())
                        .invoiceLink(purchaseOrder.getInvoiceLink())
                        .invoiceDate(purchaseOrder.getInvoiceDate())
                        .build()));
    }
    //--------------------------------------------------------------------------------------------------------------------

}
