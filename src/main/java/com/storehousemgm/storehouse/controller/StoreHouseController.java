package com.storehousemgm.storehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storehousemgm.storehouse.dto.storehouserequest.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.storehouseresponse.StoreHouseResponse;
import com.storehousemgm.storehouse.service.StoreHouseService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class StoreHouseController {
	
	@Autowired
	private StoreHouseService storeHouseService;

	@PreAuthorize("hasAuthority('CREATE_STOREHOUSE')")
	@PostMapping("/storehouses")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(@RequestBody @Valid StoreHouseRequest storeHouseRequest) {
		System.out.println(storeHouseRequest);
		return storeHouseService.addStoreHouse(storeHouseRequest);
	}

}
