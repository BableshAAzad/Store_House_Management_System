package com.storehousemgm.purchaseorder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Lob
    @Column(name = "pdf_data", columnDefinition="BLOB")
    private byte[] pdfData;
}
