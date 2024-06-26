package com.storehousemgm.storage.service.impl;

import com.storehousemgm.exception.StorageNotExistException;
import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.mapper.StorageMapper;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.storehouse.entity.StoreHouse;
import com.storehousemgm.storehouse.repository.StoreHouseRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StoreHouseRepository storeHouseRepository;

    @Autowired
    private StorageRepository storageRepository;

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<String>> addStorage(
            StorageRequest storageRequest, Long storeHouseId, Integer noOfStorageUnits) {
        StoreHouse storeHouse = storeHouseRepository.findById(storeHouseId).orElseThrow(() ->
                new StoreHouseNotExistException("StoreHouse Id : " + storeHouseId + ", is not exist"));

        Double totalCapacity = storageRequest.getCapacityWeightInKg() * noOfStorageUnits + storeHouse.getTotalCapacity();
        List<Storage> storages = new ArrayList<Storage>();
        while (noOfStorageUnits > 0) {
            Storage storage = storageMapper.mapStorageRequestToStorage(storageRequest, new Storage());
            storage.setStoreHouse(storeHouse);
            storages.add(storage);
            noOfStorageUnits--;
        }
        storages = storageRepository.saveAll(storages);

        storeHouse.setTotalCapacity(totalCapacity);
        storeHouse.setStorages(storages);
        storeHouseRepository.save(storeHouse);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<String>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Storage Created")
                .setData(storages.size() + " storages are created"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(
            StorageRequest storageRequest, Long storageId) {
        return storageRepository.findById(storageId).map(storage -> {
            Storage storage1 = storageMapper.mapStorageRequestToStorage(storageRequest, new Storage());
            storage1.setStorageId(storageId);

            StoreHouse storeHouse = storage.getStoreHouse();
            storage1.setStoreHouse(storeHouse);
            Double totalCapacity = storageRequest.getCapacityWeightInKg() + storeHouse.getTotalCapacity() - storage.getCapacityInWeight();
            storeHouse.setTotalCapacity(totalCapacity);

            storeHouseRepository.save(storeHouse);
            storage = storageRepository.save(storage1);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<StorageResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Storage Updated")
                    .setData(storageMapper.mapStorageToStorageResponse(storage)));
        }).orElseThrow(() -> new StorageNotExistException("StorageId : " + storageId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<StorageResponse>> getStorage(Long storageId) {
        return storageRepository.findById(storageId).map(storage->{
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<StorageResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Storage Founded")
                    .setData(storageMapper.mapStorageToStorageResponse(storage)));
        }).orElseThrow(() -> new StorageNotExistException("StorageId : " + storageId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<StorageResponse>>> getStorages() {
        List<StorageResponse> listStorages = storageRepository
                .findAll()
                .stream()
                .map(storageMapper::mapStorageToStorageResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<StorageResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("Storages Founded")
                .setData(listStorages));
    }
    //--------------------------------------------------------------------------------------------------------------------

}
