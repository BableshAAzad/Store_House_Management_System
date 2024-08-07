package com.storehousemgm.address.service;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AddressService {

    ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@Valid AddressRequest addressRequest,@Valid Long storeHouseId);

    ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@Valid AddressRequest addressRequest,@Valid Long addressId);

    ResponseEntity<ResponseStructure<AddressResponse>> findAddress(@Valid Long addressId);

    ResponseEntity<ResponseStructure<List<AddressResponse>>> addresses();

    ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> findStoreHousesAddress(
            @Valid String city, int page, int size);

    ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> findStoreHousesWithAddressForClient(
            @Valid Long clientId, int page, int size);
}
