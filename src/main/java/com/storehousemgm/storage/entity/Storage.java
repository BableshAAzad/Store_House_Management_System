package com.storehousemgm.storage.entity;

import com.storehousemgm.enums.MaterialType;
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
    private Double capacityInWeight;
    private Double lengthInMeters;
    private Double breadthInMeters;
    private Double heightInMeters;
    @Enumerated
    private List<MaterialType> materialTypes;
    private Double maxAdditionalWeightInKg;
    private Double availableArea;
    @ManyToOne
    private StoreHouse storeHouse;
}
