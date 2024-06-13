package com.storehousemgm.storage.dto;

import com.storehousemgm.enums.MaterialType;
import lombok.Getter;

import java.util.List;

@Getter
public class StorageRequest {
    private String blockName;
    private String section;
    private Double capacityWeightInKg;

    private Double lengthInMeters;
    private Double breadthInMeters;
    private Double heightInMeters;

    private List<MaterialType> materialTypes;

}
