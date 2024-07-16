package com.storehousemgm.inventory.mapper;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        inventory.setPrice(inventoryRequest.getPrice());
        inventory.setDescription(inventoryRequest.getDescription());
        inventory.setProductImage(inventoryRequest.getProductImage());
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
                .price(inventory.getPrice())
                .description(inventory.getDescription())
                .productImage(inventory.getProductImage())
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
                .price(inventory.getPrice())
                .description(inventory.getDescription())
                .productImage(inventory.getProductImage())
                .materialTypes(inventory.getMaterialTypes())
                .restockedAt(inventory.getRestockedAt())
                .sellerId(inventory.getSellerId())
                .stocks(mapStokeToStockResponse(inventory.getStocks()))
                .build();
    }

    private List<StockResponse> mapStokeToStockResponse(List<Stock> stocks) {
        List<StockResponse> stockResponses = new ArrayList<>();
        for (Stock stock : stocks) {
            StockResponse stockResponse = StockResponse.builder()
                    .stockId(stock.getStockId())
                    .quantity(stock.getQuantity())
                    .build();
            stockResponses.add(stockResponse);
        }
        return stockResponses;
    }
}
