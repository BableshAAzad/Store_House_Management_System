package com.storehousemgm.storagetype.mapper;

import com.storehousemgm.storagetype.dto.StorageTypeRequest;
import com.storehousemgm.storagetype.dto.StorageTypeResponse;
import com.storehousemgm.storagetype.entity.StorageType;
import org.springframework.stereotype.Component;

@Component
public class StorageTypeMapper {

    public StorageType mapStorageTypeRequestToStorageType( StorageTypeRequest storageTypeRequest, StorageType storageType){
        storageType.setLengthInMeters(storageTypeRequest.getLengthInMeters());
        storageType.setBreadthInMeters(storageTypeRequest.getBreadthInMeters());
        storageType.setHeightInMeters(storageTypeRequest.getHeightInMeters());
        storageType.setCapacityWeightInKg(storageTypeRequest.getCapacityWeightInKg());
       return storageType;
    }

    public StorageTypeResponse mapStorageTypeToStorageTypeResponse(StorageType storageType){
        return StorageTypeResponse.builder()
                .storageTypeId(storageType.getStorageTypeId())
                .lengthInMeters(storageType.getLengthInMeters())
                .breadthInMeters(storageType.getBreadthInMeters())
                .heightInMeters(storageType.getHeightInMeters())
                .capacityWeightInKg(storageType.getCapacityWeightInKg())
                .unitsAvailable(storageType.getUnitsAvailable())
                .build();
    }
}
