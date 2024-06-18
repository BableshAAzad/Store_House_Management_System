package com.storehousemgm.inventory.dto;

import com.storehousemgm.enums.MaterialType;
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
    private Double lengthInMeters;
    private Double breadthInMeters;
    private Double heightInMeters;
    private Double weightInKg;
    private Integer quantity;
    private List<MaterialType> materialTypes;
    private LocalDate restockedAt;
    private Long sellerId;
}
