package com.storehousemgm.inventory.entity;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.storage.entity.Storage;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Inventory {
    @Id
    private Long inventoryId;
    private String productTitle;
    private Double lengthInMeters;
    private Double breadthInMeters;
    private Double heightInMeters;
    private Double WeightInKg;
    private Integer quantity;
    private List<MaterialType> materialTypes;
    private Double restockedAt;
    private Long sellerId;

    @ManyToMany
    private List<Storage> storages;
}
