package com.storehousemgm.stock.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockResponse {
    private Long stockId;
    private int quantity;
}
