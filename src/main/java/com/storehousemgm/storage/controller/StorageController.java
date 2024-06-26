package com.storehousemgm.storage.controller;

import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.utility.ErrorStructure;
import com.storehousemgm.utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StorageController {

    @Autowired
    private StorageService storageService;
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to add the Storage data to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Storage Created"),
                    @ApiResponse(responseCode = "400", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PostMapping("/storehouses/{storeHouseId}/storages")
    @PreAuthorize("hasAuthority('CREATE_STORAGE')")
    public ResponseEntity<ResponseStructure<String>> addStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            @PathVariable @Valid Long storeHouseId,
            @RequestParam("no_of_storage_units") Integer noOfStorageUnits){
      return storageService.addStorage(storageRequest, storeHouseId, noOfStorageUnits);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to update the Storage data to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Storage updated"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PutMapping("/storages/{storageId}")
    @PreAuthorize("hasAuthority('UPDATE_STORAGE')")
    public ResponseEntity<ResponseStructure<StorageResponse>> updateStorage(
            @RequestBody @Valid StorageRequest storageRequest,
            @PathVariable @Valid Long storageId){
        return storageService.updateStorage(storageRequest, storageId);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the Storage data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Storage founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/storages/{storageId}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<ResponseStructure<StorageResponse>> getStorage(@PathVariable @Valid Long storageId){
        return  storageService.getStorage(storageId);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the all Storages data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Storages are founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/storages")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<ResponseStructure<List<StorageResponse>>> getStorages(){
        return storageService.getStorages();
    }
    //--------------------------------------------------------------------------------------------------------------------

}
