package com.storehousemgm.storage.entity;

import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.storehouse.entity.StoreHouse;
import jakarta.persistence.*;
import lombok.*;

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
    private Integer capacityInArea;
    private Integer capacityInWeight;
    @Enumerated
    private MaterialType materialType;
    private Integer maxAdditionalWeight;
    private Integer availableArea=0;
    @ManyToOne
    private StoreHouse storeHouse;
}
