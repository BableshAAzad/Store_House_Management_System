package com.storehousemgm.purchaseorder.entity;


import com.storehousemgm.inventory.entity.Inventory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    private Long orderId;
    private int orderQuantity;
    private String invoiceLink;
    private Long customerId;
    private LocalDate invoiceDate;

    @ManyToMany
    private List<Inventory> inventories;
}
