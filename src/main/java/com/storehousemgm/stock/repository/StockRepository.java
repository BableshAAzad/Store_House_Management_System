package com.storehousemgm.stock.repository;

import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByInventory(Inventory inventory);

}
