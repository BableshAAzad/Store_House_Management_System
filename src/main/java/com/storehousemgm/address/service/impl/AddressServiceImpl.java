package com.storehousemgm.address.service.impl;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.address.mapper.AddressMapper;
import com.storehousemgm.address.entity.Address;
import com.storehousemgm.address.repository.AddressRepository;
import com.storehousemgm.address.service.AddressService;
import com.storehousemgm.client.repository.ClientRepository;
import com.storehousemgm.exception.AddressNotExistException;
import com.storehousemgm.exception.ClientNotExistException;
import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import com.storehousemgm.storehouse.repository.StoreHouseRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private StoreHouseRepository storeHouseRepository;

    @Autowired
    private ClientRepository clientRepository;
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, Long storeHouseId) {
        return storeHouseRepository.findById(storeHouseId).map(storeHouse -> {
            Address address = addressMapper.mapAddressRequestToAddress(addressRequest, new Address());
            address.setStoreHouse(storeHouse);

            storeHouseRepository.save(storeHouse);
            Address savedAddress = addressRepository.save(address);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<AddressResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Address Created")
                    .setData(addressMapper.mapAddressToAddressResponse(savedAddress)));
        }).orElseThrow(() -> new StoreHouseNotExistException("StoreHouse Id : " + storeHouseId + ", is not exist"));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequest addressRequest, Long addressId) {
        return addressRepository.findById(addressId).map(address -> {
            Address address1 = addressMapper.mapAddressRequestToAddress(addressRequest, new Address());
            address1.setAddressId(address.getAddressId());
            address1.setStoreHouse(address.getStoreHouse());
            address = addressRepository.save(address1);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AddressResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Address Updated")
                    .setData(addressMapper.mapAddressToAddressResponse(address)));
        }).orElseThrow(() -> new AddressNotExistException("AddressId : " + addressId + ", is not exist"));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(Long addressId) {
        return addressRepository.findById(addressId).map(address -> {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AddressResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Address Updated")
                    .setData(addressMapper.mapAddressToAddressResponse(address)));
        }).orElseThrow(() -> new AddressNotExistException("AddressId : " + addressId + ", is not exist"));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<AddressResponse>>> addresses() {
        List<AddressResponse> listAddressResponse = addressRepository
                .findAll()
                .stream()
                .map(addressMapper::mapAddressToAddressResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<AddressResponse>>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Addresses Founded")
                .setData(listAddressResponse));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> findStoreHousesAddress(String city) {
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        addressRepository.findByCity(city).forEach(address -> {
            Map<String, Object> mapStoreHouseRes = new HashMap<String, Object>();
            mapStoreHouseRes.put("StoreHouseId", address.getStoreHouse().getStoreHouseId());
            mapStoreHouseRes.put("Name", address.getStoreHouse().getName());
            mapStoreHouseRes.put("TotalCapacityInKg", address.getStoreHouse().getTotalCapacityInKg());
            mapStoreHouseRes.put("Address", addressMapper.mapAddressToAddressResponse(address));
            res.add(mapStoreHouseRes);
        });
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseStructure<List<Map<String, Object>>>()
                        .setStatus(HttpStatus.FOUND.value())
                        .setMessage("StoreHouses Founded")
                        .setData(res));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<Map<String, Object>>>> findStoreHousesWithAddressForClient(Long clientId) {
        clientRepository.findById(clientId).orElseThrow(() -> new ClientNotExistException("Client Id : " + clientId + ", does not exists"));

        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
        addressRepository.findAll().forEach(address -> {
            Map<String, Object> mapStoreHouseRes = new HashMap<String, Object>();
            mapStoreHouseRes.put("StoreHouseId", address.getStoreHouse().getStoreHouseId());
            mapStoreHouseRes.put("Name", address.getStoreHouse().getName());
            mapStoreHouseRes.put("TotalCapacityInKg", address.getStoreHouse().getTotalCapacityInKg());
            mapStoreHouseRes.put("Address", addressMapper.mapAddressToAddressResponse(address));
            res.add(mapStoreHouseRes);
        });
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new ResponseStructure<List<Map<String, Object>>>()
                        .setStatus(HttpStatus.FOUND.value())
                        .setMessage("StoreHouses Founded")
                        .setData(res));
    }

    //--------------------------------------------------------------------------------------------------------------------
}
