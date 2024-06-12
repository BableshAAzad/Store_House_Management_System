package com.storehousemgm.admin.service;

import org.springframework.http.ResponseEntity;

import com.storehousemgm.admin.dto.AdminRequest;
import com.storehousemgm.admin.dto.AdminResponse;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;

public interface AdminService {
	
	ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(@Valid AdminRequest adminRequest);

	ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(@Valid AdminRequest adminRequest, @Valid Long storeHouseId);

    ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@Valid AdminRequest adminRequest);

    ResponseEntity<ResponseStructure<AdminResponse>> deleteAdmin(@Valid Long storeHouseId);

    ResponseEntity<ResponseStructure<AdminResponse>> updateAdminBySuperAdmin(@Valid AdminRequest adminRequest, @Valid Long adminId);

    ResponseEntity<ResponseStructure<AdminResponse>> findAdmin(@Valid Long adminId);

    ResponseEntity<ResponseStructure<List<AdminResponse>>> findAdmins();
}
