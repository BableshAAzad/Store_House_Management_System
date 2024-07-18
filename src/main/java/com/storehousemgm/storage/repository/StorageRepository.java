package com.storehousemgm.storage.repository;

import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storehouse.entity.StoreHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    List<Storage> findBySellerId(Long sellerId);
    List<Storage> findByStoreHouse(StoreHouse storeHouse);
}
