package com.storehousemgm.storagetype.controller;

import com.storehousemgm.storagetype.dto.StorageTypeRequest;
import com.storehousemgm.storagetype.dto.StorageTypeResponse;
import com.storehousemgm.storagetype.service.StorageTypeService;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StorageTypeController {

    @Autowired
    private StorageTypeService storageTypeService;
    //--------------------------------------------------------------------------------------------------------------------

    @PostMapping("/storages/{storageId}/storageTypes")
    public ResponseEntity<ResponseStructure<StorageTypeResponse>> addStorageType(
            @RequestBody StorageTypeRequest storageTypeRequest, @Valid @PathVariable Long storageId){
      return storageTypeService.addStorageType(storageTypeRequest, storageId);
    }
    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/storageTypes/{storageTypeId}")
    public ResponseEntity<ResponseStructure<StorageTypeResponse>> findStorageType(@PathVariable Long storageTypeId){
        return storageTypeService.findStorageType(storageTypeId);
    }
    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/storageTypes")
    public ResponseEntity<ResponseStructure<List<StorageTypeResponse>>> findStorageTypes(){
        return storageTypeService.findStorageTypes();
    }
    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------


}
