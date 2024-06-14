package com.storehousemgm.storehouse.service;

import org.springframework.http.ResponseEntity;

import com.storehousemgm.storehouse.dto.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;

public interface StoreHouseService {

	ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(@Valid StoreHouseRequest storeHouseRequest);

	ResponseEntity<ResponseStructure<StoreHouseResponse>> updateStoreHouse(@Valid StoreHouseRequest storeHouseRequest, @Valid Long storeHouseId);

    ResponseEntity<ResponseStructure<StoreHouseResponse>> findStoreHouse(@Valid Long storeHouseId);

	ResponseEntity<ResponseStructure<StoreHouseResponse>> deleteStoreHouse(@Valid Long storeHouseId);

	ResponseEntity<ResponseStructure<List<StoreHouseResponse>>> findStoreHouses();

}
