package com.storehousemgm.inventory.mapper;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryMapper {
    @Autowired
    private StockMapper stockMapper;

//    @Autowired
//    private StockRepository stockRepository;

    public Inventory mapInventoryRequestToInventory(InventoryRequest inventoryRequest, Inventory inventory) {
        inventory.setProductTitle(inventoryRequest.getProductTitle());
        inventory.setLengthInMeters(inventoryRequest.getLengthInMeters());
        inventory.setBreadthInMeters(inventoryRequest.getBreadthInMeters());
        inventory.setHeightInMeters(inventoryRequest.getHeightInMeters());
        inventory.setWeightInKg(inventoryRequest.getWeightInKg());
//        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setMaterialTypes(inventoryRequest.getMaterialTypes());
        inventory.setSellerId(inventoryRequest.getSellerId());
        return inventory;
    }

    public InventoryResponse mapInventoryToInventoryResponse(Inventory inventory, Stock stock) {
//        List<StockResponse> listStockResponses = inventory.getStocks().stream().map(stock-> stockMapper.mapStockToStockResponse(stock)).toList();
       StockResponse stockResponse = stockMapper.mapStockToStockResponse(stock);
        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .productTitle(inventory.getProductTitle())
                .lengthInMeters(inventory.getLengthInMeters())
                .breadthInMeters(inventory.getBreadthInMeters())
                .heightInMeters(inventory.getHeightInMeters())
                .weightInKg(inventory.getWeightInKg())
                .materialTypes(inventory.getMaterialTypes())
                .restockedAt(inventory.getRestockedAt())
                .sellerId(inventory.getSellerId())
                .stocks(List.of(stockResponse))
                .build();
    }

    public InventoryResponse mapInventoryToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .productTitle(inventory.getProductTitle())
                .lengthInMeters(inventory.getLengthInMeters())
                .breadthInMeters(inventory.getBreadthInMeters())
                .heightInMeters(inventory.getHeightInMeters())
                .weightInKg(inventory.getWeightInKg())
                .materialTypes(inventory.getMaterialTypes())
                .restockedAt(inventory.getRestockedAt())
                .sellerId(inventory.getSellerId())
                .build();
    }
}
