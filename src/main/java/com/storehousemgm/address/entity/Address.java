package com.storehousemgm.address.entity;

import com.storehousemgm.storehouse.entity.StoreHouse;
import jakarta.persistence.*;

abstract

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Integer pincode;
    private String longitude;
    private String latitude;

    @OneToOne
    private StoreHouse storeHouse;
}
