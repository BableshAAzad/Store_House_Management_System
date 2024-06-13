package com.storehousemgm.storage.controller;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/storehouses/{storeHouseId}/storages")
    @PreAuthorize("hasAuthority('CREATE_STORAGE')")
    public ResponseEntity<ResponseStructure<String>> addStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            @PathVariable @Valid Long storeHouseId,
            @RequestParam("no_of_storage_units") Integer noOfStorageUnits){
      return storageService.addStorage(storageRequest, storeHouseId, noOfStorageUnits);
    }

    @PutMapping("/storages/{storageId}")
    @PreAuthorize("hasAuthority('UPDATE_STORAGE')")
    public ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            @PathVariable @Valid Long storageId){
        return storageService.updateStorage(storageRequest, storageId);
    }
}
