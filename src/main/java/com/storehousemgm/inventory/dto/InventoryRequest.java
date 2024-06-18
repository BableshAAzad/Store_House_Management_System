package com.storehousemgm.inventory.dto;

import com.storehousemgm.enums.MaterialType;
import lombok.Getter;

import java.util.List;

@Getter
public class InventoryRequest {
    private String productTitle;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double weightInKg;
    private int quantity;
    private List<MaterialType> materialTypes;
    private Long sellerId;
}
