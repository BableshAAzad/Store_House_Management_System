package com.storehousemgm.inventory.controller;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.service.InventoryService;
import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.utility.ErrorStructure;
import com.storehousemgm.utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Inventory Endpoints", description = "Contains all the endpoints that are related to the Inventory entity")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to add the Inventory data to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Inventory Created"),
                    @ApiResponse(responseCode = "400", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PostMapping("/clients/{clientId}/storages/{storageId}/inventories")
    public ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            @Valid @RequestBody InventoryRequest inventoryRequest,
            @Valid @PathVariable Long storageId,
            @Valid @RequestParam int quantity,
            @Valid @PathVariable Long clientId ) {
        return inventoryService.addInventory(inventoryRequest, storageId, clientId, quantity);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to update the Inventory data to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Inventory updated"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PutMapping("/clients/inventories/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(
            @Valid @RequestBody InventoryRequest inventoryRequest,
            @Valid @PathVariable Long inventoryId) {
        return inventoryService.updateInventory(inventoryRequest, inventoryId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to find the Inventory data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Inventory founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/inventories/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(
            @Valid @PathVariable Long inventoryId) {
        return inventoryService.findInventory(inventoryId);
    }
    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to find the Inventory data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Inventory founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/inventories")
    public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findInventories(){
        return inventoryService.findInventories();
    }

    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/inventories/sellers/{sellerId}")
    public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findInventoriesBySellerId(@PathVariable Long sellerId){
        return inventoryService.findInventoriesBySellerId(sellerId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Operation(description = "The endpoint is used to update the Stock data to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Stock updated"),
                    @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PutMapping("/stocks/{stockId}")
    public ResponseEntity<ResponseStructure<StockResponse>> updateStock(
            @Valid @RequestBody StockRequest stockRequest,
            @Valid @PathVariable Long stockId){
        return inventoryService.updateStock(stockRequest, stockId);
    }
    //--------------------------------------------------------------------------------------------------------------------

}
