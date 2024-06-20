package com.storehousemgm.purchaseorder.service.impl;

import com.storehousemgm.exception.InventoryNotExistException;
import com.storehousemgm.exception.PurchaseOrderNotCompletedException;
import com.storehousemgm.exception.PurchaseOrderNotExistException;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.inventory.repository.InventoryRepository;
import com.storehousemgm.purchaseorder.dto.PurchaseOrderRequest;
import com.storehousemgm.purchaseorder.dto.PurchaseOrderResponse;
import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import com.storehousemgm.purchaseorder.mapper.PurchaseOrderMapper;
import com.storehousemgm.purchaseorder.repository.PurchaseOrderRepository;
import com.storehousemgm.purchaseorder.service.PurchaseOrderService;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> addPurchaseOrder(
            PurchaseOrderRequest purchaseOrderRequest, Long inventoryId) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            PurchaseOrder purchaseOrder = purchaseOrderMapper.mapPurchaseOrderRequestToPurchaseOrder(purchaseOrderRequest, new PurchaseOrder());
            purchaseOrder.setInvoiceLink(UUID.randomUUID().toString().concat(".jpg"));

            int availableQuantity = inventory.getStocks().getFirst().getQuantity();
            if(availableQuantity>=purchaseOrderRequest.getOrderQuantity()) {
                int temp = availableQuantity - purchaseOrder.getOrderQuantity();
                Stock stock = inventory.getStocks().getFirst();
                stock.setQuantity(temp);
                inventory.getStocks().addFirst(stock);

                inventory = inventoryRepository.save(inventory);

                purchaseOrder.setInventories(List.of(inventory));
                purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
            }else {
                throw new PurchaseOrderNotCompletedException("Please reduce quantity");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<PurchaseOrderResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("PurchaseOrder Created")
                    .setData(purchaseOrderMapper.mapPurchaseOrderToPurchaseOrderResponse(purchaseOrder)));
        }).orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
    }

    //--------------------------------------------------------------------------------------------------------------------
//    note : this method is only for demo purpose
    @Override
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> updatePurchaseOrder(
            PurchaseOrderRequest purchaseOrderRequest, Long orderId) {
        return purchaseOrderRepository.findById(orderId).map(purchaseOrder -> {
            int oldOrderQnt = purchaseOrder.getOrderQuantity();
            int newOrderQnt = purchaseOrderRequest.getOrderQuantity();

//         TODO note :-> this method is working only if purchase order have only one inventory
            List<Inventory> listInventories = purchaseOrder.getInventories();
            if (newOrderQnt > oldOrderQnt) {
                int updateOrderQnt = newOrderQnt - oldOrderQnt;
                listInventories.forEach(inventory -> {
//                    inventory.setQuantity(inventory.getQuantity()-updateOrderQnt);
                    inventoryRepository.save(inventory);
                });
            } else {
                int updateOrderQnt = oldOrderQnt - newOrderQnt;
                listInventories.forEach(inventory -> {
//                    inventory.setQuantity(inventory.getQuantity()+updateOrderQnt);
                    inventoryRepository.save(inventory);
                });
            }
            purchaseOrder = purchaseOrderMapper.mapPurchaseOrderRequestToPurchaseOrder(purchaseOrderRequest, purchaseOrder);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<PurchaseOrderResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("PurchaseOrder Updated")
                    .setData(purchaseOrderMapper.mapPurchaseOrderToPurchaseOrderResponse(purchaseOrder)));
        }).orElseThrow(() -> new PurchaseOrderNotExistException("OrderId : " + orderId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> findPurchaseOrder(Long orderId) {
        return purchaseOrderRepository.findById(orderId).map(purchaseOrder -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<PurchaseOrderResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("PurchaseOrder Founded")
                    .setData(purchaseOrderMapper.mapPurchaseOrderToPurchaseOrderResponse(purchaseOrder)));
        }).orElseThrow(() -> new PurchaseOrderNotExistException("OrderId : " + orderId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<PurchaseOrderResponse>>> findPurchaseOrders() {
        List<PurchaseOrderResponse> listPurchaseOrders = purchaseOrderRepository.findAll()
                .stream()
                .map(purchaseOrder ->
                        purchaseOrderMapper.mapPurchaseOrderToPurchaseOrderResponse(purchaseOrder)).toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<PurchaseOrderResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("PurchaseOrders are Founded")
                .setData(listPurchaseOrders));
    }
    //--------------------------------------------------------------------------------------------------------------------

}
