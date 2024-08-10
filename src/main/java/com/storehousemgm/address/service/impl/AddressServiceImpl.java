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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
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


    private PagedModel.PageMetadata getPageMetadata(Page<?> page) {
        return new PagedModel.PageMetadata(
                page.getSize(),
                page.getNumber(),
                page.getTotalElements()
        );
    }
    private <T> PagedModel<T> getPagedModel(Page<T> page) {
        return PagedModel.of(page.getContent(), getPageMetadata(page));
    }
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
    public ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> findStoreHousesAddress(
            String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Address> addressPage = addressRepository.findByCityContaining(city, pageable);

        List<Map<String, Object>> res = addressPage.stream().map(address -> {
            Map<String, Object> mapStoreHouseRes = new HashMap<>();
            mapStoreHouseRes.put("StoreHouseId", address.getStoreHouse().getStoreHouseId());
            mapStoreHouseRes.put("Name", address.getStoreHouse().getName());
            mapStoreHouseRes.put("TotalCapacityInKg", address.getStoreHouse().getTotalCapacityInKg());
            mapStoreHouseRes.put("Address", addressMapper.mapAddressToAddressResponse(address));
            return mapStoreHouseRes;
        }).toList();
        Page<Map<String, Object>> resPage = new PageImpl<>(res, pageable, addressPage.getTotalElements());
        PagedModel<Map<String, Object>> pagedModel = getPagedModel(resPage);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseStructure<PagedModel<Map<String, Object>>>()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("StoreHouses Founded")
                        .setData(pagedModel));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<PagedModel<Map<String, Object>>>> findStoreHousesWithAddressForClient(
            Long clientId, int page, int size) {
        clientRepository.findById(clientId).orElseThrow(() -> new ClientNotExistException("Client Id : " + clientId + ", does not exists"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Address> addressPage = addressRepository.findAll(pageable);

        List<Map<String, Object>> res = addressPage.stream().map(address -> {
            Map<String, Object> mapStoreHouseRes = new HashMap<>();
            mapStoreHouseRes.put("StoreHouseId", address.getStoreHouse().getStoreHouseId());
            mapStoreHouseRes.put("Name", address.getStoreHouse().getName());
            mapStoreHouseRes.put("TotalCapacityInKg", address.getStoreHouse().getTotalCapacityInKg());
            mapStoreHouseRes.put("Address", addressMapper.mapAddressToAddressResponse(address));
            return mapStoreHouseRes;
        }).toList();
        Page<Map<String, Object>> resPage = new PageImpl<>(res, pageable, addressPage.getTotalElements());
        PagedModel<Map<String, Object>> pagedModel = getPagedModel(resPage);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseStructure<PagedModel<Map<String, Object>>>()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("StoreHouses Founded")
                        .setData(pagedModel));
    }

    //--------------------------------------------------------------------------------------------------------------------
}
