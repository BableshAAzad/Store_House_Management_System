package com.storehousemgm.purchaseorder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PurchaseOrderRequest {
    @Min(value = 1, message = "Order quantity must be at least 1")
    private int orderQuantity;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
}
