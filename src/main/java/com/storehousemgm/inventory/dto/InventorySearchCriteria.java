package com.storehousemgm.inventory.dto;

import com.storehousemgm.enums.MaterialType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class InventorySearchCriteria {
    private String productTitle;
    private List<MaterialType> materialTypes;
    private String description;
    private Double minPrice;
    private Double maxPrice;
    private String sortOrder;
}
