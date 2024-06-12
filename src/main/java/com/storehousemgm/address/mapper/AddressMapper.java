package com.storehousemgm.address.mapper;

import com.storehousemgm.address.dto.AddressRequest;
import com.storehousemgm.address.dto.AddressResponse;
import com.storehousemgm.address.entity.Address;
import com.storehousemgm.storehouse.mapper.StoreHouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    @Autowired
    private StoreHouseMapper storeHouseMapper;

    public Address mapAddressRequestToAddress(AddressRequest addressRequest, Address address){
        address.setAddressLine(addressRequest.getAddressLine());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setCountry(addressRequest.getCountry());
        address.setPincode(addressRequest.getPincode());
        address.setLongitude(addressRequest.getLongitude());
        address.setLatitude(addressRequest.getLatitude());
        return  address;
    }
    public AddressResponse mapAddressToAddressResponse(Address address){
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .addressLine(address.getAddressLine())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pincode(address.getPincode())
                .longitude(address.getLongitude())
                .latitude(address.getLatitude())
                .build();
    }
}
