package com.storehousemgm.storehouse.service.impl;

import com.storehousemgm.address.repository.AddressRepository;
import com.storehousemgm.admin.entity.Admin;
import com.storehousemgm.admin.repository.AdminRepository;
import com.storehousemgm.exception.StoreHouseNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.storehousemgm.storehouse.dto.StoreHouseRequest;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.storehouse.entity.StoreHouse;
import com.storehousemgm.storehouse.mapper.StoreHouseMapper;
import com.storehousemgm.storehouse.repository.StoreHouseRepository;
import com.storehousemgm.storehouse.service.StoreHouseService;
import com.storehousemgm.utility.ResponseStructure;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreHouseServiceImpl implements StoreHouseService {

    @Autowired
    private StoreHouseRepository storeHouseRepository;

    @Autowired
    private StoreHouseMapper storeHouseMapper;

    @Autowired
    private AdminRepository adminRepository;

//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<StoreHouseResponse>> addStoreHouse(
            @Valid StoreHouseRequest storeHouseRequest) {
        StoreHouse storeHouse = storeHouseMapper.mapStoreHouseRequestToStoreHouse(storeHouseRequest, new StoreHouse());
        StoreHouse savedStoreHouse = storeHouseRepository.save(storeHouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<StoreHouseResponse>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Store House Created")
                .setData(storeHouseMapper.mapStoreHouseToStoreHouseResponse(savedStoreHouse)));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<StoreHouseResponse>> updateStoreHouse(
            StoreHouseRequest storeHouseRequest, Long storeHouseId) {
        return storeHouseRepository.findById(storeHouseId).map(storeHouse -> {
            storeHouse.setName(storeHouseRequest.getName());
            StoreHouse savedStoreHouse = storeHouseRepository.save(storeHouse);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<StoreHouseResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("StoreHouse updated")
                    .setData(storeHouseMapper.mapStoreHouseToStoreHouseResponse(savedStoreHouse)));
        }).orElseThrow(() -> new StoreHouseNotExistException("StoreHouse Not Exist in id : " + storeHouseId));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<StoreHouseResponse>> findStoreHouse(Long storeHouseId) {
        return storeHouseRepository.findById(storeHouseId).map(storeHouse -> {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<StoreHouseResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("StoreHouseFounded")
                    .setData(storeHouseMapper.mapStoreHouseToStoreHouseResponse(storeHouse)));
        }).orElseThrow(() -> new StoreHouseNotExistException("StoreHouse Id : " + storeHouseId + ", is not present in database"));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<StoreHouseResponse>> deleteStoreHouse(Long storeHouseId) {
        return storeHouseRepository.findById(storeHouseId).map(storeHouse -> {
            storeHouseRepository.delete(storeHouse);
            Admin admin = storeHouse.getAdmin();
            if (admin != null)
                adminRepository.delete(admin);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<StoreHouseResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Store House Deleted")
                    .setData(storeHouseMapper.mapStoreHouseToStoreHouseResponse(storeHouse)));
        }).orElseThrow(() -> new StoreHouseNotExistException("StoreHouse Id : " + storeHouseId + ", is not exist"));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<StoreHouseResponse>>> findStoreHouses() {
       List<StoreHouseResponse> storeHouses = storeHouseRepository.findAll()
                .stream()
                .map(storeHouse -> storeHouseMapper.mapStoreHouseToStoreHouseResponse(storeHouse))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<StoreHouseResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("StoreHouses Founded")
                .setData(storeHouses));
    }
//--------------------------------------------------------------------------------------------------------------------

}
