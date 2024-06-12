package com.storehousemgm.storage.mapper;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.entity.Storage;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {
    public Storage mapStorageRequestToStorage(StorageRequest storageRequest, Storage storage){
        Integer capacityInArea = storageRequest.getWidth() *storageRequest.getLength()*storageRequest.getBreadth();
        storage.setBlockName(storageRequest.getBlockName());
        storage.setSection(storageRequest.getSection());
        storage.setCapacityInArea(capacityInArea);
        storage.setCapacityInWeight(storageRequest.getCapacityInWeight());
        storage.setMaterialType(storageRequest.getMaterialType());
       return storage;
    }
    public StorageResponse mapStorageToStorageResponse(Storage storage){
        return StorageResponse.builder()
                .storageId(storage.getStorageId())
                .blockName(storage.getBlockName())
                .section(storage.getSection())
                .capacityInArea(storage.getCapacityInArea())
                .capacityInWeight(storage.getCapacityInWeight())
                .materialType(storage.getMaterialType())
                .maxAdditionalWeight(storage.getMaxAdditionalWeight())
                .availableArea(storage.getAvailableArea())
                .build();
    }
}
