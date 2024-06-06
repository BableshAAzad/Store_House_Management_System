package com.storehousemgm.mapper;

import org.springframework.stereotype.Component;

import com.storehousemgm.admin.entity.Admin;
import com.storehousemgm.requestdto.AdminRequest;
import com.storehousemgm.responsedto.AdminResponse;

@Component
public class AdminMapper {
// this method is convert AdminRequest object to Admin object which is used to save or other operation
	public Admin mapAdminRequestToAdmin(AdminRequest adminRequest, Admin admin) {
		admin.setName(adminRequest.getName());
		admin.setEmail(adminRequest.getEmail());
		admin.setPassword(adminRequest.getPassword());
		return admin;
	}
	
//	This method is convert admin object to AdminResponse which is used for response
	public AdminResponse mapAdminToAdminResponse(Admin admin) {
		return AdminResponse.builder()
				.adminId(admin.getAdminId())
				.name(admin.getName())
				.email(admin.getEmail()).build();
	}
}
