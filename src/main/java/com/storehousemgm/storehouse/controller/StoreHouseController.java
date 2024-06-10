package com.storehousemgm.storehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storehousemgm.storehouse.dto.storehouserequest.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.storehouseresponse.StoreHouseResponse;
import com.storehousemgm.storehouse.service.StoreHouseService;
import com.storehousemgm.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@EnableMethodSecurity
public class StoreHouseController {
	
	@Autowired
	private StoreHouseService storoHouseService;

	@PreAuthorize("hasAuthority('CREATE_STOREHOUSE')")
	@PostMapping("/storehouses")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(@RequestBody @Valid StoreHouseRequest storeHouseRequest) {
		return storoHouseService.addStoreHouse(storeHouseRequest);
	}

}
