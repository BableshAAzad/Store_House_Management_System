package com.storehousemgm.storage.service;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

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

    ResponseEntity<ResponseStructure<StorageResponse>> findFirstStorageUnderCriteria(@Valid double capacityInWeight,
                                                                                     @Valid double lengthInMeters,
                                                                                     @Valid double breadthInMeters,
                                                                                     @Valid double heightInMeters);

    ResponseEntity<ResponseStructure<List<Map<String, Double>>>> findAllTheStoragesAvailable(@Valid double capacityInWeight,
                                                                                              @Valid double lengthInMeters,
                                                                                              @Valid double breadthInMeters,
                                                                                              @Valid double heightInMeters);
}
