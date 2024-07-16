package com.storehousemgm.address.dto;

import com.storehousemgm.storehouse.dto.StoreHouseResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {
    private Long addressId;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Integer pincode;
    private String longitude;
    private String latitude;
}
