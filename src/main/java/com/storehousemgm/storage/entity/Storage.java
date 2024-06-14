package com.storehousemgm.storage.entity;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.storehouse.entity.StoreHouse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageId;
    private String blockName;
    private String section;
    private double capacityInWeight;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    @Enumerated
    private List<MaterialType> materialTypes;
    private double maxAdditionalWeightInKg;
    private double availableArea;

    @ManyToOne
    private StoreHouse storeHouse;

    @ManyToMany
    private List<Inventory> inventories;
}
