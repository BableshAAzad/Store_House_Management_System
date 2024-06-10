package com.storehousemgm.admin.service;

import org.springframework.http.ResponseEntity;

import com.storehousemgm.admin.dto.requestdto.AdminRequest;
import com.storehousemgm.admin.dto.responsedto.AdminResponse;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface AdminService {
	
	ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(@Valid AdminRequest adminRequest);

	ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(@Valid AdminRequest adminRequest, @Valid Long storeHouseId);

}
