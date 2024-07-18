package com.storehousemgm.storage.service;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StorageService {
    ResponseEntity<ResponseStructure<String>> addStorage(
            @Valid StorageRequest storageRequest,
            @Valid Long storeHouseId,
            @Valid Long storageTypeId,
            @Valid int noOfStorageUnits);

    ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(
            @Valid StorageRequest storageRequest, @Valid Long storageId);

    ResponseEntity<ResponseStructure<StorageResponse>> getStorage(@Valid Long storageId);

    ResponseEntity<ResponseStructure<List<StorageResponse>>> getStorages();

    ResponseEntity<ResponseStructure<List<StorageResponse>>> getStoragesBySellerId(Long sellerId);

    ResponseEntity<ResponseStructure<List<StorageResponse>>> getStoragesByStoreHouseId(Long storeHouseId);
}
