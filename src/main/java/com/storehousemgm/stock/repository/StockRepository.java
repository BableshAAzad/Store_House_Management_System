package com.storehousemgm.stock.repository;

import com.storehousemgm.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByQuantity(int quantity);
}
