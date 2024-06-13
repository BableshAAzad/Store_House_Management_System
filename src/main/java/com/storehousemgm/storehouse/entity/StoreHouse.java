package com.storehousemgm.storehouse.entity;

import com.storehousemgm.address.entity.Address;
import com.storehousemgm.admin.entity.Admin;

import com.storehousemgm.storage.entity.Storage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class StoreHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeHouseId;
	private String name;
	private Double totalCapacity=0.0;

	@OneToOne
	private Admin admin;

	@OneToMany(mappedBy = "storeHouse")
	private List<Storage> storages;

}
