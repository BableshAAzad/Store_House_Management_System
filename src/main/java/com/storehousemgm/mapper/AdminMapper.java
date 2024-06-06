package com.storehousemgm.mapper;

import org.springframework.stereotype.Component;

import com.storehousemgm.entity.Admin;
import com.storehousemgm.requestdto.AdminRequest;
import com.storehousemgm.responsedto.AdminResponse;

@Component
public class AdminMapper {

	public Admin mapAdminRequestToAdmin(AdminRequest adminRequest, Admin admin) {
		admin.setName(adminRequest.getName());
		admin.setEmail(adminRequest.getEmail());
		admin.setPassword(adminRequest.getPassword());
		return admin;
	}
	
	public AdminResponse mapAdminToAdminResponse(Admin admin) {
		return AdminResponse.builder()
				.adminId(admin.getAdminId())
				.name(admin.getName())
				.email(admin.getEmail()).build();
	}
}
