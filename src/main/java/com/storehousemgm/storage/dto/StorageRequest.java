package com.storehousemgm.storage.dto;

import com.storehousemgm.enums.MaterialType;
import lombok.Getter;

@Getter
public class StorageRequest {
    private String blockName;
    private String section;
    private Integer length;
    private Integer width;
    private Integer breadth;
    private Integer capacityInWeight;
    private MaterialType materialType;
}
