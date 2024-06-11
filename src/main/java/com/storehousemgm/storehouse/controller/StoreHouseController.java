package com.storehousemgm.storehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.storehousemgm.storehouse.dto.storehouserequest.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.storehouseresponse.StoreHouseResponse;
import com.storehousemgm.storehouse.service.StoreHouseService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StoreHouseController {
	
	@Autowired
	private StoreHouseService storeHouseService;

	@PreAuthorize("hasAuthority('CREATE_STOREHOUSE')")
	@PostMapping("/storehouses")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(
			@RequestBody @Valid StoreHouseRequest storeHouseRequest) {
		return storeHouseService.addStoreHouse(storeHouseRequest);
	}

	@PreAuthorize("hasAuthority('UPDATE_STOREHOUSE')")
	@PutMapping("/storehouses/{storeHouseId}")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> updateStoreHouse(
			@RequestBody @Valid StoreHouseRequest storeHouseRequest,
			@PathVariable @Valid Long storeHouseId){
		return storeHouseService.updateStoreHouse(storeHouseRequest, storeHouseId);
	}

	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/storehouses/{storeHouseId}")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> findStoreHouse(
			@PathVariable @Valid Long storeHouseId){
		return storeHouseService.findStoreHouse(storeHouseId);
	}

	@PreAuthorize("hasAuthority('DELETE_STOREHOUSE')")
	@DeleteMapping("/storehouses/{storeHouseId}")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> deleteStoreHouse(
			@PathVariable @Valid Long storeHouseId){
		return storeHouseService.deleteStoreHouse(storeHouseId);
	}

	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/storehouses")
	public ResponseEntity<ResponseStructure<List<StoreHouseResponse>>> findStoreHouses(){
		return storeHouseService.findStoreHouses();
	}

}
