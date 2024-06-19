package com.storehousemgm.purchaseorder.entity;


import com.storehousemgm.inventory.entity.Inventory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private int orderQuantity;
    private String invoiceLink;
    private Long customerId;

    @ManyToMany
    private List<Inventory> inventories;
}
