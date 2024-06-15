package com.storehousemgm.storage.controller;

import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storage.dto.StorageRequest;
import com.storehousemgm.storage.dto.StorageResponse;
import com.storehousemgm.storage.mapper.StorageMapper;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.storage.service.StorageService;
import com.storehousemgm.utility.ErrorStructure;
import com.storehousemgm.utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Storage Endpoints", description = "Contains all the endpoints that are related to the Storage entity")
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
            @RequestParam("no_of_storage_units") int noOfStorageUnits){
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

    @GetMapping("/findFirstStorage/{capacityInWeight}/{lengthInMeters}/{breadthInMeters}/{heightInMeters}")
    public ResponseEntity<ResponseStructure<StorageResponse>> findFirstStorageUnderCriteria(
                                    @PathVariable double capacityInWeight,
                                    @PathVariable double lengthInMeters,
                                    @PathVariable double breadthInMeters,
                                    @PathVariable double heightInMeters){
        return storageService.findFirstStorageUnderCriteria(capacityInWeight,  lengthInMeters, breadthInMeters, heightInMeters);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @GetMapping("/findAllTheStoragesAvailable/{capacityInWeight}/{lengthInMeters}/{breadthInMeters}/{heightInMeters}")
    public ResponseEntity<ResponseStructure<List<Map<String, Double>>>> findAllTheStoragesAvailable(@PathVariable double capacityInWeight,
                                                                                                     @PathVariable double lengthInMeters,
                                                                                                     @PathVariable double breadthInMeters,
                                                                                                     @PathVariable double heightInMeters){
        return storageService.findAllTheStoragesAvailable(capacityInWeight,  lengthInMeters, breadthInMeters, heightInMeters);
    }

    //--------------------------------------------------------------------------------------------------------------------

}
