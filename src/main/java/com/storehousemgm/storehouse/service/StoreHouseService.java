package com.storehousemgm.storehouse.service;

import org.springframework.http.ResponseEntity;

import com.storehousemgm.storehouse.dto.storehouserequest.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.storehouseresponse.StoreHouseResponse;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface StoreHouseService {

	ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(@Valid StoreHouseRequest storeHouseRequest);
}
