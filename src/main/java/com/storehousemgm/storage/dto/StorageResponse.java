package com.storehousemgm.storage.dto;

import com.storehousemgm.enums.MaterialType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageResponse {
    private Long storageId;
    private String blockName;
    private String section;
    private List<MaterialType> materialTypes;
    private Double maxAdditionalWeightInKg;
    private Double availableArea;
    private Long storeHouseId;
    private Long sellerId;
}
