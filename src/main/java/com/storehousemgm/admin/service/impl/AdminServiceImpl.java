package com.storehousemgm.admin.service.impl;



import com.storehousemgm.exception.AdminNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.storehousemgm.admin.dto.requestdto.AdminRequest;
import com.storehousemgm.admin.dto.responsedto.AdminResponse;
import com.storehousemgm.admin.entity.Admin;
import com.storehousemgm.admin.enums.AdminType;
import com.storehousemgm.admin.mapper.AdminMapper;
import com.storehousemgm.admin.repository.AdminRepository;
import com.storehousemgm.admin.service.AdminService;
import com.storehousemgm.exception.IllegalOperationException;
import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storehouse.repository.StoreHoseRepository;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.Collection;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private StoreHoseRepository storeHouseRepository;

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(@Valid AdminRequest adminRequest) {
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
	public ResponseEntity<ResponseStructure<AdminResponse>> addAdmin(@Valid AdminRequest adminRequest, @Valid Long storeHouseId) {
		return storeHouseRepository.findById(storeHouseId).map(existStoreHouse->{
			Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest, new Admin());
			admin.setAdminType(AdminType.ADMIN);
			admin = adminRepository.save(admin);
			
			existStoreHouse.setAdmin(admin);
			storeHouseRepository.save(existStoreHouse);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<AdminResponse>()
					.setStatus(HttpStatus.CREATED.value())
					.setMessage("Admin Created")
					.setData(adminMapper.mapAdminToAdminResponse(admin)));
		}).orElseThrow(()-> new StoreHouseNotExistException("StoreHouseId : "+storeHouseId+", is not exist"));
	}

	@Override
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(AdminRequest adminRequest) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return adminRepository.findByEmail(email).map(admin->{
			admin.setName(adminRequest.getName());
			admin.setEmail(adminRequest.getEmail());
			admin.setPassword(adminRequest.getPassword());
			 Admin savedAdmin = adminRepository.save(admin);
			 return  ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AdminResponse>()
					 .setStatus(HttpStatus.OK.value())
					 .setMessage("Admin is updated")
					 .setData(adminMapper.mapAdminToAdminResponse(savedAdmin)));
		}).orElseThrow(()->new AdminNotFoundException("User not found in database"));
	}

}
