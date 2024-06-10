package com.storehousemgm.storehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.storehousemgm.storehouse.dto.storehouserequest.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.storehouseresponse.StoreHouseResponse;
import com.storehousemgm.storehouse.entity.StoreHouse;
import com.storehousemgm.storehouse.mapper.StoreHouseMapper;
import com.storehousemgm.storehouse.repository.StoreHoseRepository;
import com.storehousemgm.storehouse.service.StoreHouseService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class StoreHouseServiceImpl implements StoreHouseService{
	
	@Autowired
	private StoreHoseRepository storeHoseRepository;
	
	@Autowired
	private StoreHouseMapper storeHouseMapper;

	@Override
	public ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(
			@Valid StoreHouseRequest storeHouseRequest) {
				StoreHouse storeHouse = storeHouseMapper.mapStoreHouseRequestToStoreHouse(storeHouseRequest, new StoreHouse());
				storeHoseRepository.save(storeHouse);
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<StoreHouseResponse>()
						.setStatus(HttpStatus.CREATED.value())
						.setMessage("Store House Created")
						.setData(storeHouseMapper.mapStoreHouseToStoreHouseResponse(storeHouse)));
	}
}
