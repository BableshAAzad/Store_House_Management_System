package com.storehousemgm.storage.service.impl;

import com.storehousemgm.exception.StorageNotExistException;
import com.storehousemgm.exception.StorageTypeNotExistException;
import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.mapper.StorageMapper;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.storagetype.entity.StorageType;
import com.storehousemgm.storagetype.repository.StorageTypeRepository;
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

    @Autowired
    private StorageTypeRepository storageTypeRepository;

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<String>> addStorage(
            StorageRequest storageRequest,
            Long storeHouseId,
            Long storageTypeId,
            int noOfStorageUnits) {

        StoreHouse storeHouse = storeHouseRepository.findById(storeHouseId).orElseThrow(() ->
                new StoreHouseNotExistException("StoreHouse Id : " + storeHouseId + ", is not exist"));

        StorageType storageType = storageTypeRepository.findById(storageTypeId).orElseThrow(()->
                new StorageTypeNotExistException("StorageTypeId : "+storageTypeId+", StorageType is not exist"));

        storageType.setUnitsAvailable(storageType.getUnitsAvailable()+noOfStorageUnits);
        storageType = storageTypeRepository.save(storageType);


        double totalCapacity = storageType.getCapacityWeightInKg() * noOfStorageUnits + storeHouse.getTotalCapacityInKg();
        storeHouse.setTotalCapacityInKg(totalCapacity);
        storeHouseRepository.save(storeHouse);

        List<Storage> storages = new ArrayList<Storage>();
        while (noOfStorageUnits > 0) {
            Storage storage = storageMapper.mapStorageRequestToStorage(storageRequest, new Storage());
            storage.setStoreHouse(storeHouse);
            storage.setStorageType(storageType);
            storage.setMaxAdditionalWeightInKg(storageType.getCapacityWeightInKg());
            storage.setAvailableArea(storageType.getHeightInMeters()*storageType.getBreadthInMeters()*storageType.getLengthInMeters());

            storages.add(storage);
            noOfStorageUnits--;
        }

        storages = storageRepository.saveAll(storages);

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
            Storage storage1 = storageMapper.mapStorageRequestToStorage(storageRequest, storage);

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

    @Override
    public ResponseEntity<ResponseStructure<List<StorageResponse>>> getStoragesBySellerId(Long sellerId) {
        List<StorageResponse> listStorages = storageRepository
                .findBySellerId(sellerId)
                .stream()
                .map(storageMapper::mapStorageToStorageResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<StorageResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("Seller Storages are Founded")
                .setData(listStorages));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<StorageResponse>>> getStoragesByStoreHouseId(Long storeHouseId) {
        StoreHouse storeHouse = storeHouseRepository.findById(storeHouseId).orElseThrow(() -> new StoreHouseNotExistException("StoreHouseId : " + storeHouseId + ", is not exists"));
        List<StorageResponse> listStorages = storageRepository
                .findByStoreHouse(storeHouse)
                .stream()
                .map(storageMapper::mapStorageToStorageResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<StorageResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("Storages are Founded")
                .setData(listStorages));
    }
}
