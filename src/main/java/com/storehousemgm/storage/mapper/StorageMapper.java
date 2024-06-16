package com.storehousemgm.storage.mapper;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.entity.Storage;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {
    public Storage mapStorageRequestToStorage(StorageRequest storageRequest, Storage storage){
//        double capacityInArea = storageRequest.getHeightInMeters()
//                *storageRequest.getBreadthInMeters()
//                *storageRequest.getLengthInMeters();

        storage.setBlockName(storageRequest.getBlockName());
        storage.setSection(storageRequest.getSection());
//        storage.setCapacityInWeight(storageRequest.getCapacityWeightInKg());

//        storage.setLengthInMeters(storageRequest.getLengthInMeters());
//        storage.setBreadthInMeters(storageRequest.getBreadthInMeters());
//        storage.setHeightInMeters(storageRequest.getHeightInMeters());

        storage.setMaterialTypes(storageRequest.getMaterialTypes());
//        storage.setMaxAdditionalWeightInKg(storageRequest.getCapacityWeightInKg());
//        storage.setAvailableArea(capacityInArea);
       return storage;
    }
    public StorageResponse mapStorageToStorageResponse(Storage storage){
        return StorageResponse.builder()
                .storageId(storage.getStorageId())
                .blockName(storage.getBlockName())
                .section(storage.getSection())
//                .capacityInWeight(storage.getCapacityInWeight())
//
//                .lengthInMeters(storage.getLengthInMeters())
//                .breadthInMeters(storage.getBreadthInMeters())
//                .heightInMeters(storage.getHeightInMeters())

                .materialTypes(storage.getMaterialTypes())
                .maxAdditionalWeightInKg(storage.getMaxAdditionalWeightInKg())
                .availableArea(storage.getAvailableArea())
                .build();
    }
}
