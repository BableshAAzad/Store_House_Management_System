package com.storehousemgm.storage.repository;

import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storehouse.entity.StoreHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StorageRepository extends JpaRepository<Storage, Long>, JpaSpecificationExecutor<Storage> {
    Page<Storage> findBySellerId(Long sellerId, Pageable pageable);
    Page<Storage> findByStoreHouse(StoreHouse storeHouse, Pageable pageable);
}
