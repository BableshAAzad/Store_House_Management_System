package com.storehousemgm.stock.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class StockRequest {
    @Min(value = 1, message = "Stock quantity must be at least 1")
    private int quantity;
}
