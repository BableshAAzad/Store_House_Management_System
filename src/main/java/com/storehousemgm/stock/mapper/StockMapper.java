package com.storehousemgm.stock.mapper;

import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.stock.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
    public StockResponse mapStockToStockResponse(Stock stock) {
        return StockResponse.builder()
                .stockId(stock.getStockId())
                .quantity(stock.getQuantity())
                .build();
    }
}
