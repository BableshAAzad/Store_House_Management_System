package com.storehousemgm.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehousemgm.admin.entity.Admin;
import com.storehousemgm.enums.AdminType;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	boolean existsByAdminType(AdminType adminType);
	
	Optional<Admin> findByEmail(String email);

	List<Admin> findAllByAdminType(AdminType adminType);
}
