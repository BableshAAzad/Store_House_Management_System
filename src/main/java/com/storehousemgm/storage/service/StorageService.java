package com.storehousemgm.storage.service;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface StorageService {
    ResponseEntity<ResponseStructure<StorageResponse>> addStorage(@Valid StorageRequest storageRequest,@Valid Long storeHouseId);
}
