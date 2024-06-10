package com.storehousemgm.storehouse.mapper;

import org.springframework.stereotype.Component;

import com.storehousemgm.storehouse.dto.storehouserequest.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.storehouseresponse.StoreHouseResponse;
import com.storehousemgm.storehouse.entity.StoreHouse;

@Component
public class StoreHouseMapper {
    
	public StoreHouse mapStoreHouseRequestToStoreHouse(StoreHouseRequest storeHouseRequest, StoreHouse storeHouse) {
		storeHouse.setName(storeHouseRequest.getStoreHouseName());
		return storeHouse;
	}
	
	public StoreHouseResponse mapStoreHouseToStoreHouseResponse(StoreHouse storeHouse) {
		return StoreHouseResponse.builder().storeHouseId(storeHouse.getStoreHouseId())
				.storeHoseName(storeHouse.getName())
				.totalCapacity(storeHouse.getTotalCapacity())
				.build();
	}
}
