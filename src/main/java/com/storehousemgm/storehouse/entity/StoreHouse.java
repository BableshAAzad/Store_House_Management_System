package com.storehousemgm.storehouse.entity;

import com.storehousemgm.admin.entity.Admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StoreHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeHouseId;
	private String name;
	private Long totalCapacity=0l;
	@OneToOne
	private Admin admin;
}
