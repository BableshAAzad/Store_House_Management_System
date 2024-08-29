package com.storehousemgm.stock.service;

import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface StockService {
    ResponseEntity<ResponseStructure<StockResponse>> updateStock(@Valid StockRequest stockRequest, @Valid Long stockId);

    ResponseEntity<ResponseStructure<StockResponse>> getStock(@Valid Long stockId);
}
