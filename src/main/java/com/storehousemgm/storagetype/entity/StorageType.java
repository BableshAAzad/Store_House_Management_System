package com.storehousemgm.storagetype.entity;

import com.storehousemgm.storage.entity.Storage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storageTypeId;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double capacityWeightInKg;
    private int unitsAvailable;

}
