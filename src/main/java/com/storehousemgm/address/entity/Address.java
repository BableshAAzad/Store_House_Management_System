package com.storehousemgm.address.entity;

import com.storehousemgm.storehouse.entity.StoreHouse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private int pincode;
    private String longitude;
    private String latitude;

    @OneToOne
    private StoreHouse storeHouse;
}
