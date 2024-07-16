package com.storehousemgm.storagetype.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageTypeResponse {

    private Long storageTypeId;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double capacityWeightInKg;
    private int unitsAvailable;

}
