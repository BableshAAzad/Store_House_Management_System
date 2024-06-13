package com.storehousemgm.storage.service;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface StorageService {
    ResponseEntity<ResponseStructure<String>> addStorage(
            @Valid StorageRequest storageRequest, @Valid Long storeHouseId, @Valid Integer noOfStorageUnits);

    ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(
            @Valid StorageRequest storageRequest, @Valid Long storageId);
}
