package com.storehousemgm.storage.service;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.hateoas.PagedModel;
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

    ResponseEntity<ResponseStructure<PagedModel<StorageResponse>>> getStoragesBySellerId(
            Long sellerId, int page, int size);

    ResponseEntity<ResponseStructure<PagedModel<StorageResponse>>> getStoragesByStoreHouseId(
            Long storeHouseId, int page, int size);
}
