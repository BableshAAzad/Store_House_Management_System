package com.storehousemgm.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.storehousemgm.admin.dto.requestdto.AdminRequest;
import com.storehousemgm.admin.dto.responsedto.AdminResponse;
import com.storehousemgm.admin.service.AdminService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(
			@RequestBody @Valid AdminRequest adminRequest) {
		return adminService.addSuperAdmin(adminRequest);
	}
	
	@PostMapping("/storehouses/{storeHouseId}/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(
			@RequestBody @Valid AdminRequest adminRequest, @PathVariable("storeHouseId") Long storeHouseId){
		return adminService.addAdmin(adminRequest, storeHouseId);
	}

	@PreAuthorize("hasAuthority('DELETE_ADMIN')")
	@DeleteMapping("/storehouses/{storeHouseId}/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> deleteAdmin(
			@PathVariable("storeHouseId") Long storeHouseId){
		return adminService.deleteAdmin(storeHouseId);
	}
	
    @PutMapping("/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(
			@RequestBody @Valid AdminRequest adminRequest) {
		return adminService.updateAdmin(adminRequest);
	}

	@PreAuthorize("hasAuthority('UPDATE_ADMIN')")
	@PutMapping("/admins/{adminId}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdminBySuperAdmin(
			@RequestBody @Valid AdminRequest adminRequest, @PathVariable @Valid Long adminId){
		return adminService.updateAdminBySuperAdmin(adminRequest, adminId);
	}

	@GetMapping("/admins/{adminId}")
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdmin(@PathVariable @Valid Long adminId){
		return adminService.findAdmin(adminId);
	}

	@GetMapping("/admins")
	public  ResponseEntity<ResponseStructure<List<AdminResponse>>> findAdmins(){
		return  adminService.findAdmins();
	}

}
