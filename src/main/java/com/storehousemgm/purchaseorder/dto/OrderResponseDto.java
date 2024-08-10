package com.storehousemgm.purchaseorder.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private String inventoryTitle;
    private String inventoryImage;
    private String invoiceLink;
    private LocalDate invoiceDate;
}
