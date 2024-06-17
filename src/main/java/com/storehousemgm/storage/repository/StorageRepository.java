package com.storehousemgm.storage.repository;

import com.storehousemgm.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {

}
