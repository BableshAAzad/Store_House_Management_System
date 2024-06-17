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

    @PostMapping("/storageTypes")
    public ResponseEntity<ResponseStructure<StorageTypeResponse>> addStorageType(
            @RequestBody StorageTypeRequest storageTypeRequest){

      return storageTypeService.addStorageType(storageTypeRequest);
    }
    //--------------------------------------------------------------------------------------------------------------------
    @PutMapping("/storageTypes/{storageTypeId}")
    public ResponseEntity<ResponseStructure<StorageTypeResponse>> updateStorageType(
            @RequestBody StorageTypeRequest storageTypeRequest,
            @PathVariable Long storageTypeId){
        return storageTypeService.updateStorageType(storageTypeRequest, storageTypeId);
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
