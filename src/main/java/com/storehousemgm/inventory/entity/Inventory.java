package com.storehousemgm.inventory.entity;

import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.client.entity.Client;
import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import com.storehousemgm.storage.entity.Storage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    private Long inventoryId;
    private String productTitle;
    private double lengthInMeters;
    private double breadthInMeters;
    private double heightInMeters;
    private double weightInKg;
    private double price;
    private String description;
    private String productImage;
    @Enumerated
    private Set<MaterialType> materialTypes;
    private LocalDate restockedAt;
    private LocalDate updatedInventoryAt;
    private Long sellerId;

    @ManyToMany
    private List<Storage> storages;

    @ManyToOne
    private Client client;

    @ManyToMany(mappedBy = "inventories")
    private List<PurchaseOrder> purchaseOrders;

    @OneToMany(mappedBy = "inventory")
    private List<Stock> stocks;
}
