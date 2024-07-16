package com.storehousemgm.storehouse.controller;

import com.storehousemgm.utility.ErrorStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.storehousemgm.storehouse.dto.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.storehouse.service.StoreHouseService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "StoreHouse Endpoints", description = "Contains all the endpoints that are related to the StoreHouse entity")
public class StoreHouseController {
	
	@Autowired
	private StoreHouseService storeHouseService;
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to add the StoreHouse data to the database",
			responses = {
					@ApiResponse(responseCode = "201", description = "StoreHouse Created"),
					@ApiResponse(responseCode = "400", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PreAuthorize("hasAuthority('CREATE_STOREHOUSE')")
	@PostMapping("/storehouses")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(
			@RequestBody @Valid StoreHouseRequest storeHouseRequest) {
		return storeHouseService.addStoreHouse(storeHouseRequest);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to update the StoreHouse data to the database",
			responses = {
					@ApiResponse(responseCode = "200", description = "StoreHouse updated"),
					@ApiResponse(responseCode = "404", description = "Invalid Id", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PreAuthorize("hasAuthority('UPDATE_STOREHOUSE')")
	@PutMapping("/storehouses/{storeHouseId}")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> updateStoreHouse(
			@RequestBody @Valid StoreHouseRequest storeHouseRequest,
			@PathVariable @Valid Long storeHouseId){
		return storeHouseService.updateStoreHouse(storeHouseRequest, storeHouseId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to find the StoreHouse data to the database",
			responses = {
					@ApiResponse(responseCode = "302", description = "StoreHouse founded"),
					@ApiResponse(responseCode = "404", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
//	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/storehouses/{storeHouseId}")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> findStoreHouse(
			@PathVariable @Valid Long storeHouseId){
		return storeHouseService.findStoreHouse(storeHouseId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to delete the StoreHouse data to the database",
			responses = {
					@ApiResponse(responseCode = "200", description = "StoreHouse deleted"),
					@ApiResponse(responseCode = "404", description = "Invalid Id", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PreAuthorize("hasAuthority('DELETE_STOREHOUSE')")
	@DeleteMapping("/storehouses/{storeHouseId}")
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> deleteStoreHouse(
			@PathVariable @Valid Long storeHouseId){
		return storeHouseService.deleteStoreHouse(storeHouseId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to find the all StoreHouses data to the database",
			responses = {
					@ApiResponse(responseCode = "302", description = "StoreHouses founded"),
					@ApiResponse(responseCode = "404", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
//	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/storehouses")
	public ResponseEntity<ResponseStructure<List<StoreHouseResponse>>> findStoreHouses(){
		return storeHouseService.findStoreHouses();
	}

//--------------------------------------------------------------------------------------------------------------------

	@GetMapping("/test")
	public String test(){
		return "Hello Bablesh";
	}
}
