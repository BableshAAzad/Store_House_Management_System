package com.storehousemgm.storehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehousemgm.storehouse.entity.StoreHouse;

import java.util.List;

public interface StoreHouseRepository extends JpaRepository<StoreHouse, Long> {

}
