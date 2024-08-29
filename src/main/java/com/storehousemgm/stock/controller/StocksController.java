package com.storehousemgm.stock.controller;

import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.stock.service.StockService;
import com.storehousemgm.utility.ErrorStructure;
import com.storehousemgm.utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Stock Endpoints", description = "Contains all the endpoints that are related to the Stock entity")
public class StocksController {

    private final StockService stockService;

    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to update the Stock data to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock updated"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PutMapping("/clients/stocks/{stockId}")
    public ResponseEntity<ResponseStructure<StockResponse>> updateStock(
            @Valid @RequestBody StockRequest stockRequest,
            @Valid @PathVariable Long stockId) {
        return stockService.updateStock(stockRequest, stockId);
    }
    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to get the Stock data to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/clients/stocks/{stockId}")
    public ResponseEntity<ResponseStructure<StockResponse>> getStock(
            @Valid @PathVariable Long stockId) {
        return stockService.getStock(stockId);
    }

}
