package com.storehousemgm.storagetype.repository;

import com.storehousemgm.storagetype.entity.StorageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageTypeRepository extends JpaRepository<StorageType, Long> {
        boolean existsByLengthInMetersAndBreadthInMetersAndHeightInMetersAndCapacityWeightInKg(
            double lengthInMeters,
            double breadthInMeters,
            double heightInMeters,
            double capacityWeightInKg);
}
