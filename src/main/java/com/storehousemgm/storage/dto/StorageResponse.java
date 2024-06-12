package com.storehousemgm.storage.dto;

import com.storehousemgm.enums.MaterialType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageResponse {
    private Long storageId;
    private String blockName;
    private String section;
    private Integer capacityInArea;
    private Integer capacityInWeight;
    private MaterialType materialType;
    private Integer maxAdditionalWeight;
    private Integer availableArea;
}
