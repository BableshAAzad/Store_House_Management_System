package com.storehousemgm.storage.repository;

import com.storehousemgm.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    Optional<Storage> findFirstByCapacityInWeightAndLengthInMetersAndBreadthInMetersAndHeightInMeters(
            double capacityInWeight,
            double lengthInMeters,
            double breadthInMeters,
            double heightInMeters);

    List<Storage> findAllByCapacityInWeightAndLengthInMetersAndBreadthInMetersAndHeightInMeters(
            double capacityInWeight,
            double lengthInMeters,
            double breadthInMeters,
            double heightInMeters);
}
