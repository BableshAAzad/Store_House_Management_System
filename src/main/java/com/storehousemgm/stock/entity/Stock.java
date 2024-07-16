package com.storehousemgm.stock.entity;

import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.storage.entity.Storage;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    private int quantity;

    @ManyToOne
    private Storage storage;

    @ManyToOne
    private Inventory inventory;
}
