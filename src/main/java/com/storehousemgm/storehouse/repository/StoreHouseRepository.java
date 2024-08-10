package com.storehousemgm.storehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehousemgm.storehouse.entity.StoreHouse;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StoreHouseRepository extends JpaRepository<StoreHouse, Long>, JpaSpecificationExecutor<StoreHouse> {

}
