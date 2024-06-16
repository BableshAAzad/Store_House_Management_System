package com.storehousemgm.storagetype.dto;

import lombok.Getter;

@Getter
public class StorageTypeRequest {

    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double capacityInWeight;

}
