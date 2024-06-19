package com.storehousemgm.purchaseorder.repository;

import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
