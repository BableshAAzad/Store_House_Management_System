package com.storehousemgm.storagetype.service;

import com.storehousemgm.storagetype.dto.StorageTypeRequest;
import com.storehousemgm.storagetype.dto.StorageTypeResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StorageTypeService {
    ResponseEntity<ResponseStructure<StorageTypeResponse>> addStorageType(
            @Valid StorageTypeRequest storageTypeRequest,@Valid Long storageId);

    ResponseEntity<ResponseStructure<StorageTypeResponse>> findStorageType(@Valid Long storageTypeId);

    ResponseEntity<ResponseStructure<List<StorageTypeResponse>>> findStorageTypes();
}
