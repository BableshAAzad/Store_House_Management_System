package com.storehousemgm.address.service.impl;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.address.mapper.AddressMapper;
import com.storehousemgm.address.entity.Address;
import com.storehousemgm.address.repository.AddressRepository;
import com.storehousemgm.address.service.AddressService;
import com.storehousemgm.exception.AddressNotExistException;
import com.storehousemgm.exception.StoreHouseNotExistException;
import com.storehousemgm.storehouse.repository.StoreHouseRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private StoreHouseRepository storeHouseRepository;
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
      return addressRepository.findById(addressId).map(address->{
          Address address1 = addressMapper.mapAddressRequestToAddress(addressRequest, new Address());
          address1.setAddressId(address.getAddressId());
          address1.setStoreHouse(address.getStoreHouse());
          address = addressRepository.save(address1);
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AddressResponse>()
                  .setStatus(HttpStatus.OK.value())
                  .setMessage("Address Updated")
                  .setData(addressMapper.mapAddressToAddressResponse(address)));
      }).orElseThrow(()->new AddressNotExistException("AddressId : "+addressId+", is not exist"));
    }
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<AddressResponse>> findAddress(Long addressId) {
        return addressRepository.findById(addressId).map(address->{
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<AddressResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Address Updated")
                    .setData(addressMapper.mapAddressToAddressResponse(address)));
        }).orElseThrow(()->new AddressNotExistException("AddressId : "+addressId+", is not exist"));
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

}
