package com.storehousemgm.inventory.entity;

import com.storehousemgm.client.entity.Client;
import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.storage.entity.Storage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    private String productTitle;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double weightInKg;
    private int quantity;
    private List<MaterialType> materialTypes;
    private LocalDate restockedAt;
    private Long sellerId;

    @ManyToMany
    private List<Storage> storages;

    @ManyToOne
    private Client client;
}
