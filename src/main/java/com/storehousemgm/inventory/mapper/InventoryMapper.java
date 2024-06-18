package com.storehousemgm.inventory.mapper;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public Inventory mapInventoryRequestToInventory(InventoryRequest inventoryRequest, Inventory inventory) {
        inventory.setProductTitle(inventoryRequest.getProductTitle());
        inventory.setLengthInMeters(inventoryRequest.getLengthInMeters());
        inventory.setBreadthInMeters(inventoryRequest.getBreadthInMeters());
        inventory.setHeightInMeters(inventoryRequest.getHeightInMeters());
        inventory.setWeightInKg(inventoryRequest.getWeightInKg());
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setMaterialTypes(inventoryRequest.getMaterialTypes());
        inventory.setSellerId(inventoryRequest.getSellerId());
        return inventory;
    }

    public InventoryResponse mapInventoryToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .productTitle(inventory.getProductTitle())
                .lengthInMeters(inventory.getLengthInMeters())
                .breadthInMeters(inventory.getBreadthInMeters())
                .heightInMeters(inventory.getHeightInMeters())
                .weightInKg(inventory.getWeightInKg())
                .quantity(inventory.getQuantity())
                .materialTypes(inventory.getMaterialTypes())
                .restockedAt(inventory.getRestockedAt())
                .sellerId(inventory.getSellerId())
                .build();
    }
}
