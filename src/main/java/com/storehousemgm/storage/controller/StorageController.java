package com.storehousemgm.storage.controller;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/storehouses/{storeHouseId}/storages")
    public ResponseEntity<ResponseStructure<StorageResponse>> addStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            @PathVariable @Valid Long storeHouseId){
      return storageService.addStorage(storageRequest, storeHouseId);
    }
}
