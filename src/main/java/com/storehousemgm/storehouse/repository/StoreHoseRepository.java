package com.storehousemgm.storehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storehousemgm.storehouse.entity.StoreHouse;

import java.util.Optional;

public interface StoreHoseRepository extends JpaRepository<StoreHouse, Long> {

}
