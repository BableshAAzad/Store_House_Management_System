package com.storehousemgm.admin.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.storehousemgm.admin.dto.AdminRequest;
import com.storehousemgm.admin.dto.AdminResponse;
import com.storehousemgm.admin.entity.Admin;

@Component
public class AdminMapper {
	@Autowired
	private PasswordEncoder passwordEncoder;

// this method is convert AdminRequest object to Admin object which is used to save or other operation
	public Admin mapAdminRequestToAdmin(AdminRequest adminRequest, Admin admin) {
		admin.setName(adminRequest.getName());
		admin.setEmail(adminRequest.getEmail());
		admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
		return admin;
	}

//	This method is convert admin object to AdminResponse which is used for response
	public AdminResponse mapAdminToAdminResponse(Admin admin) {
		return AdminResponse.builder().adminId(admin.getAdminId()).name(admin.getName()).email(admin.getEmail())
				.build();
	}
}
