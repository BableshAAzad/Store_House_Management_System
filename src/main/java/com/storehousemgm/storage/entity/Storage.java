package com.storehousemgm.storage.entity;

import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.storagetype.entity.StorageType;
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
    @Enumerated(EnumType.STRING)
    private List<MaterialType> materialTypes;
    private double maxAdditionalWeightInKg;
    private double availableArea;
    private Long sellerId;

    @ManyToOne
    private StoreHouse storeHouse;

    @ManyToMany(mappedBy = "storages")
    private List<Inventory> inventories;

    @ManyToOne
    private StorageType storageType;

    @OneToMany(mappedBy = "storage")
    private List<Stock> stocks;
}
