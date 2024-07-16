package com.storehousemgm.purchaseorder.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseOrderResponse {
    private Long orderId;
    private int orderQuantity;
    private String invoiceLink;
    private Long customerId;
}
