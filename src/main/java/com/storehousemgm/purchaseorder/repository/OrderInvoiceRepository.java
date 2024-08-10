package com.storehousemgm.purchaseorder.repository;

import com.storehousemgm.purchaseorder.entity.OrderInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderInvoiceRepository extends JpaRepository<OrderInvoice, Long> {
    Optional<OrderInvoice> findByOrderId(Long orderId);
}
