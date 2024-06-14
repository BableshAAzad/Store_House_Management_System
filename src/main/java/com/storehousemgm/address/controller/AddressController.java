package com.storehousemgm.address.controller;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.address.service.AddressService;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.storehouse.service.StoreHouseService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private StoreHouseService storeHouseService;
//--------------------------------------------------------------------------------------------------------------------

    @PreAuthorize("hasAuthority('CREATE_ADDRESS')")
    @PostMapping("/storehouses/{storeHouseId}/addresses")
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(
            @RequestBody @Valid AddressRequest addressRequest,
            @PathVariable @Valid Long storeHouseId) {
        return addressService.addAddress(addressRequest, storeHouseId);
    }
//--------------------------------------------------------------------------------------------------------------------

    @PreAuthorize("hasAuthority('UPDATE_ADDRESS')")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(
            @RequestBody @Valid AddressRequest addressRequest,
            @PathVariable @Valid Long addressId) {
        return addressService.updateAddress(addressRequest, addressId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(@PathVariable @Valid Long addressId) {
        return addressService.findAddress(addressId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/addresses")
    public ResponseEntity<ResponseStructure<List<AddressResponse>>> addresses() {
        return addressService.addresses();
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the all StoreHouses data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "StoreHouses founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/cities/{city}/storehouses")
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> findStoreHouses(@PathVariable @Valid String city){
        return addressService.findStoreHousesAddress(city);
    }
}
