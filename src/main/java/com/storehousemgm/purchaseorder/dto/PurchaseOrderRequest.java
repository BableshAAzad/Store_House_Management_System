package com.storehousemgm.purchaseorder.dto;

import lombok.Getter;

@Getter
public class PurchaseOrderRequest {
    private int orderQuantity;
    private Long customerId;
}
