package com.storehousemgm.purchaseorder.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequestDto {
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    @Min(value = 1, message = "Order quantity must be at least 1")
    private int totalQuantity;
    private double totalPrice;
    private double discountPrice;
    private double totalPayableAmount;

    private AddressDto addressDto;
}
