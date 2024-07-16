package com.storehousemgm.admin.service.impl;


import com.storehousemgm.exception.AdminAlreadyExistException;
import com.storehousemgm.exception.AdminNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.storehousemgm.admin.dto.AdminRequest;
import com.storehousemgm.admin.dto.AdminResponse;
import com.storehousemgm.admin.entity.Admin;
import com.storehousemgm.enums.AdminType;
import com.storehousemgm.admin.mapper.AdminMapper;
import com.storehousemgm.admin.repository.AdminRepository;
import com.storehousemgm.admin.service.AdminService;
import com.storehousemgm.exception.SuperAdminAlreadyExistException;
import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storehouse.repository.StoreHouseRepository;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StoreHouseRepository storeHouseRepository;

    @Override
    public ResponseEntity<ResponseStructure<AdminResponse>> addSuperAdmin(@Valid AdminRequest adminRequest) {
        if (adminRepository.existsByAdminType(AdminType.SUPER_ADMIN))
            throw new SuperAdminAlreadyExistException("Super admin already exist");
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
        return storeHouseRepository.findById(storeHouseId).map(existStoreHouse -> {
            Optional<Admin> checkEmailOptional = adminRepository.findByEmail(adminRequest.getEmail());
            if (checkEmailOptional.isEmpty()) {
                Admin admin1 = storeHouseRepository.findById(storeHouseId).get().getAdmin();
               if(admin1 == null) {
                   Admin admin = adminMapper.mapAdminRequestToAdmin(adminRequest, new Admin());
                   admin.setAdminType(AdminType.ADMIN);
                   admin = adminRepository.save(admin);
                   existStoreHouse.setAdmin(admin);
                   storeHouseRepository.save(existStoreHouse);
                   return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<AdminResponse>()
                           .setStatus(HttpStatus.CREATED.value())
                           .setMessage("Admin Created")
                           .setData(adminMapper.mapAdminToAdminResponse(admin)));
               }else{
                   throw new AdminAlreadyExistException("Admin already exist in this Store House");
               }
            } else {
                throw new AdminAlreadyExistException("Email Id : " + adminRequest.getEmail() + ", is already exist");
            }
        }).orElseThrow(() -> new StoreHouseNotExistException("StoreHouseId : " + storeHouseId + ", is not exist"));
    }

    @Override
    public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(AdminRequest adminRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminRepository.findByEmail(email).map(admin -> {
            Admin admin1 = adminMapper.mapAdminRequestToAdmin(adminRequest, new Admin());
            admin1.setAdminId(admin.getAdminId());
            admin1.setAdminType(admin.getAdminType());
            Admin savedAdmin = adminRepository.save(admin1);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AdminResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Admin is updated")
                    .setData(adminMapper.mapAdminToAdminResponse(savedAdmin)));
        }).orElseThrow(() -> new AdminNotExistException("User not found in database"));
    }

    @Override
    public ResponseEntity<ResponseStructure<AdminResponse>> deleteAdmin(Long storeHouseId) {
		return storeHouseRepository.findById(storeHouseId).map(storeHouse->{
			Admin admin = storeHouse.getAdmin();
			if(admin != null){
                storeHouse.setAdmin(null);
                storeHouseRepository.save(storeHouse);
				adminRepository.delete(admin);
                return  ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AdminResponse>()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("Admin Deleted")
                        .setData(adminMapper.mapAdminToAdminResponse(admin)));
			}else{
				throw new AdminNotExistException("Admin not found in this StoreHouse");
			}
		}).orElseThrow(()-> new StoreHouseNotExistException("StoreHouse not found in id : "+storeHouseId));
    }

    @Override
    public ResponseEntity<ResponseStructure<AdminResponse>> updateAdminBySuperAdmin(AdminRequest adminRequest, Long adminId) {
        return adminRepository.findById(adminId).map(admin -> {
            Admin admin1 = adminMapper.mapAdminRequestToAdmin(adminRequest, admin);
            Admin updatedAdmin = adminRepository.save(admin1);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AdminResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Admin Updated")
                    .setData(adminMapper.mapAdminToAdminResponse(updatedAdmin)));
        }).orElseThrow(() -> new AdminNotExistException("AdminId : " + adminId + ", is not exist"));
    }

    @Override
    public ResponseEntity<ResponseStructure<AdminResponse>> findAdmin(Long adminId) {
        return adminRepository.findById(adminId).map(admin -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<AdminResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Admin founded")
                    .setData(adminMapper.mapAdminToAdminResponse(admin)));
        }).orElseThrow(() -> new AdminNotExistException("AdminId : " + adminId + ", is not found"));
    }

    @Override
    public ResponseEntity<ResponseStructure<List<AdminResponse>>> findAdmins() {
        List<AdminResponse> admins = adminRepository
                .findAllByAdminType(AdminType.ADMIN)
                .stream()
                .map(adminMapper::mapAdminToAdminResponse).toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<AdminResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("Admins Founded")
                .setData(admins));
    }

}
