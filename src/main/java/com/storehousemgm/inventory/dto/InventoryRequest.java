package com.storehousemgm.inventory.dto;

import com.storehousemgm.enums.MaterialType;
import lombok.Getter;

import java.util.List;

@Getter
public class InventoryRequest {
    private String productTitle;
    private Double lengthInMeters;
    private Double breadthInMeters;
    private Double heightInMeters;
    private Double weightInKg;
    private Integer quantity;
    private List<MaterialType> materialTypes;
    private Long sellerId;
}
