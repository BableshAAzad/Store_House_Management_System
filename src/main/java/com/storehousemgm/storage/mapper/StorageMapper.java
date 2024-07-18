package com.storehousemgm.storage.mapper;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.entity.Storage;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {
    public Storage mapStorageRequestToStorage(StorageRequest storageRequest, Storage storage){
        storage.setBlockName(storageRequest.getBlockName());
        storage.setSection(storageRequest.getSection());
        storage.setMaterialTypes(storageRequest.getMaterialTypes());
       return storage;
    }
    public StorageResponse mapStorageToStorageResponse(Storage storage){
        return StorageResponse.builder()
                .storageId(storage.getStorageId())
                .blockName(storage.getBlockName())
                .section(storage.getSection())
                .materialTypes(storage.getMaterialTypes())
                .maxAdditionalWeightInKg(storage.getMaxAdditionalWeightInKg())
                .availableArea(storage.getAvailableArea())
                .storeHouseId(storage.getStoreHouse().getStoreHouseId())
                .sellerId(storage.getSellerId())
                .build();
    }
}
