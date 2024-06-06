package com.storehousemgm.admin.service;

import org.springframework.http.ResponseEntity;

import com.storehousemgm.requestdto.AdminRequest;
import com.storehousemgm.responsedto.AdminResponse;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface AdminService {
	
	ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(AdminRequest adminRequest);

	ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(@Valid AdminRequest adminRequest);
}
