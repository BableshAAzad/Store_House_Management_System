package com.storehousemgm.inventory.dto;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.stock.dto.StockResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private Long inventoryId;
    private String productTitle;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double weightInKg;
    private double price;
    private String description;
    private String productImage;
    private List<MaterialType> materialTypes;
    private LocalDate restockedAt;
    private Long sellerId;
    private List<StockResponse> stocks;
}
