package com.storehousemgm.storehouse.entity;

import com.storehousemgm.admin.entity.Admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class WareHouse {
	@Id
	private Long wareHouseId;
	private String name;
	@OneToOne
	private Admin admin;
}
