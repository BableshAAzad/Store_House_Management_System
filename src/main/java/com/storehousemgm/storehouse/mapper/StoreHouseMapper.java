package com.storehousemgm.storehouse.mapper;

import com.storehousemgm.admin.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.storehousemgm.storehouse.dto.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.storehouse.entity.StoreHouse;

@Component
public class StoreHouseMapper {

	@Autowired
	private AdminMapper adminMapper;
    
	public StoreHouse mapStoreHouseRequestToStoreHouse(StoreHouseRequest storeHouseRequest, StoreHouse storeHouse) {
		storeHouse.setName(storeHouseRequest.getName());
		return storeHouse;
	}
	
	public StoreHouseResponse mapStoreHouseToStoreHouseResponse(StoreHouse storeHouse) {
		return StoreHouseResponse.builder().storeHouseId(storeHouse.getStoreHouseId())
				.storeHoseName(storeHouse.getName())
				.totalCapacityInKg(storeHouse.getTotalCapacityInKg())
				.build();
	}
}
