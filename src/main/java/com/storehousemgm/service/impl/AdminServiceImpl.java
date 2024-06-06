package com.storehousemgm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.storehousemgm.admin.entity.Admin;
import com.storehousemgm.admin.enums.AdminType;
import com.storehousemgm.admin.enums.Privilege;
import com.storehousemgm.admin.service.AdminService;
import com.storehousemgm.exception.IllegalOperationException;
import com.storehousemgm.mapper.AdminMapper;
import com.storehousemgm.repository.AdminRepository;
import com.storehousemgm.requestdto.AdminRequest;
import com.storehousemgm.responsedto.AdminResponse;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AdminMapper adminMapper;

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(AdminRequest adminRequest) {
		if(adminRepository.existsByAdminType(AdminType.SUPER_ADMIN))
			throw new IllegalOperationException("Super admin already exist");
		Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest, new Admin());
		admin.setAdminType(AdminType.SUPER_ADMIN);
//		admin.setPrivileges(AdminType.SUPER_ADMIN.getPrivileges());
		admin = adminRepository.save(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<AdminResponse>()
				.setStatus(HttpStatus.CREATED.value())
				.setMessage("Super Admin Created")
				.setData(adminMapper.mapAdminToAdminResponse(admin)));
	}

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(@Valid AdminRequest adminRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
