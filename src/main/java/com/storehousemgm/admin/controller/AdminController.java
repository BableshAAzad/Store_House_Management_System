package com.storehousemgm.admin.controller;

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

import com.storehousemgm.admin.dto.AdminRequest;
import com.storehousemgm.admin.dto.AdminResponse;
import com.storehousemgm.admin.service.AdminService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Admin Endpoints", description = "Contains all the endpoints that are related to the Admin entity")
public class AdminController {
	@Autowired
	private AdminService adminService;
//--------------------------------------------------------------------------------------------------------------------
	@Operation(description = "The endpoint is used to add the SuperAdmin data to the database",
			responses = {
					@ApiResponse(responseCode = "201", description = "SuperAdmin Created"),
					@ApiResponse(responseCode = "400", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(
			@RequestBody @Valid AdminRequest adminRequest) {
		return adminService.addSuperAdmin(adminRequest);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to add the SuperAdmin data to the database",
			responses = {
					@ApiResponse(responseCode = "201", description = "SuperAdmin Created"),
					@ApiResponse(responseCode = "400", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PostMapping("/storehouses/{storeHouseId}/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(
			@RequestBody @Valid AdminRequest adminRequest, @PathVariable("storeHouseId") Long storeHouseId){
		return adminService.addAdmin(adminRequest, storeHouseId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to delete the Admin data to the database",
			responses = {
					@ApiResponse(responseCode = "200", description = "Admin deleted"),
					@ApiResponse(responseCode = "404", description = "Invalid Id", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PreAuthorize("hasAuthority('DELETE_ADMIN')")
	@DeleteMapping("/storehouses/{storeHouseId}/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> deleteAdmin(
			@PathVariable("storeHouseId") Long storeHouseId){
		return adminService.deleteAdmin(storeHouseId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to update the Admin data to the database",
			responses = {
					@ApiResponse(responseCode = "200", description = "Admin updated"),
					@ApiResponse(responseCode = "404", description = "Invalid Id", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PreAuthorize("hasAuthority('UPDATE_ADMIN')")
    @PutMapping("/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(
			@RequestBody @Valid AdminRequest adminRequest) {
		return adminService.updateAdmin(adminRequest);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to update the Admin data to the database",
			responses = {
					@ApiResponse(responseCode = "200", description = "Admin updated"),
					@ApiResponse(responseCode = "404", description = "Invalid Id", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@PreAuthorize("hasAuthority('UPDATE_ADMIN')")
	@PutMapping("/admins/{adminId}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdminBySuperAdmin(
			@RequestBody @Valid AdminRequest adminRequest, @PathVariable @Valid Long adminId){
		return adminService.updateAdminBySuperAdmin(adminRequest, adminId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to find the Admin data to the database",
			responses = {
					@ApiResponse(responseCode = "302", description = "Admin founded"),
					@ApiResponse(responseCode = "404", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@GetMapping("/admins/{adminId}")
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdmin(@PathVariable @Valid Long adminId){
		return adminService.findAdmin(adminId);
	}
//--------------------------------------------------------------------------------------------------------------------

	@Operation(description = "The endpoint is used to find the all Admins data to the database",
			responses = {
					@ApiResponse(responseCode = "302", description = "Admins founded"),
					@ApiResponse(responseCode = "404", description = "Invalid Input", content = {
							@Content(schema = @Schema(oneOf = ErrorStructure.class))
					})
			})
	@GetMapping("/admins")
	public  ResponseEntity<ResponseStructure<List<AdminResponse>>> findAdmins(){
		return  adminService.findAdmins();
	}

}
