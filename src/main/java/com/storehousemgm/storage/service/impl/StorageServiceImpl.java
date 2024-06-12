package com.storehousemgm.storage.service.impl;

import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.mapper.StorageMapper;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.storehouse.repository.StoreHouseRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StoreHouseRepository storeHouseRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Override
    public ResponseEntity<ResponseStructure<StorageResponse>> addStorage(StorageRequest storageRequest, Long storeHouseId) {
        return storeHouseRepository.findById(storeHouseId).map(storeHouse -> {

            Storage storage = storageMapper.mapStorageRequestToStorage(storageRequest, new Storage());
            Integer totalCapacity = storeHouse.getTotalCapacity() + storage.getCapacityInArea();
            storeHouse.setTotalCapacity(totalCapacity);
            storeHouseRepository.save(storeHouse);

            storage.setMaxAdditionalWeight(0);
            storage.setAvailableArea(0);
            storage = storageRepository.save(storage);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<StorageResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Storage Created")
                    .setData(storageMapper.mapStorageToStorageResponse(storage)));
        }).orElseThrow(() -> new StoreHouseNotExistException("StoreHouse Id : " + storeHouseId + ", is not exist"));
    }
}
