package com.storehousemgm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehousemgm.entity.Admin;
import com.storehousemgm.enums.AdminType;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	boolean existsByAdminType(AdminType adminType);
}
