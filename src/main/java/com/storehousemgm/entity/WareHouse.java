package com.storehousemgm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class WareHouse {
	@Id
	private Long wareHouseId;
	private String name;
	private Admin admin;
}
