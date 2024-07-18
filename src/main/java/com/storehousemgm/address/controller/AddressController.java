package com.storehousemgm.address.controller;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.address.service.AddressService;
import com.storehousemgm.storehouse.service.StoreHouseService;
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
@Tag(name = "Address Endpoints", description = "Contains all the endpoints that are related to the Address entity")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private StoreHouseService storeHouseService;
//--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to add the Address data to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Address Created"),
                    @ApiResponse(responseCode = "400", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PreAuthorize("hasAuthority('CREATE_ADDRESS')")
    @PostMapping("/storehouses/{storeHouseId}/addresses")
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(
            @RequestBody @Valid AddressRequest addressRequest,
            @PathVariable @Valid Long storeHouseId) {
        return addressService.addAddress(addressRequest, storeHouseId);
    }
//--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to update the Address data to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Address updated"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PreAuthorize("hasAuthority('UPDATE_ADDRESS')")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(
            @RequestBody @Valid AddressRequest addressRequest,
            @PathVariable @Valid Long addressId) {
        return addressService.updateAddress(addressRequest, addressId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to find the Address data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Address founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
//    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(@PathVariable @Valid Long addressId) {
        return addressService.findAddress(addressId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to find the Addresses data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Addresses founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/addresses")
    public ResponseEntity<ResponseStructure<List<AddressResponse>>> addresses() {
        return addressService.addresses();
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the all StoreHouses with address to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "StoreHouses founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/client/cities/{city}/storehouses")
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> findStoreHouseByCityForClient(@PathVariable @Valid String city){
        return addressService.findStoreHousesAddress(city);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the all StoreHouses with address to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "StoreHouses founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/cities/{city}/storehouses")
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> findStoreHouseByCityForAdmin(@PathVariable @Valid String city){
        return addressService.findStoreHousesAddress(city);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the all StoreHouses with address to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "StoreHouses founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/clients/{clientId}/storehouses")
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> findStoreHousesWithAddressForClient(@PathVariable Long clientId){
        return addressService.findStoreHousesWithAddressForClient(clientId);
    }
    //--------------------------------------------------------------------------------------------------------------------

}
