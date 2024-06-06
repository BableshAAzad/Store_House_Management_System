package com.storehousemgm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storehousemgm.entity.Admin;
import com.storehousemgm.requestdto.AdminRequest;
import com.storehousemgm.responsedto.AdminResponse;
import com.storehousemgm.service.AdminService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AdminConroller {
	@Autowired
	private AdminService adminService;

	@PostMapping("/admins/super-admin")
	public ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(@RequestBody @Valid AdminRequest adminRequest) {
		return adminService.addSuperAdmin(adminRequest);
	}
	
	@PostMapping("/admins/admin")
	public ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(@RequestBody @Valid AdminRequest adminRequest){
		return adminService.addAdmin(adminRequest);
	}
}
