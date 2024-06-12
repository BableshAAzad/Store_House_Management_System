package com.storehousemgm.address.controller;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.address.service.AddressService;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AddressController {
    @Autowired
    private AddressService addressService;
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

}
