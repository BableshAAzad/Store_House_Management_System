package com.storehousemgm.client.entity;

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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String businessName;
    private String email;
    private long contactNumber;
    private String apiKey;

    @OneToMany(mappedBy = "client")
    private List<Inventory> inventories;
}
