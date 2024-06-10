package com.storehousemgm.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.storehousemgm.admin.dto.requestdto.AdminRequest;
import com.storehousemgm.admin.dto.responsedto.AdminResponse;
import com.storehousemgm.admin.service.AdminService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AdminConroller {
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
	
    @GetMapping("/demo")
	public String demo(){
		return "Hello Bablesh";
	}

}
