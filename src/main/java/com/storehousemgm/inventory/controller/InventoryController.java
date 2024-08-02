package com.storehousemgm.inventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.dto.InventorySearchCriteria;
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
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Inventory Endpoints", description = "Contains all the endpoints that are related to the Inventory entity")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ObjectMapper objectMapper;
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
            @Valid @PathVariable Long clientId) {
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
    @GetMapping("/inventories") // GET /inventories?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.findInventories(page, size);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/inventories/sellers/{sellerId}") //inventories/sellers/sellerId?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventoriesBySellerId(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.findInventoriesBySellerId(sellerId, page, size);
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
            @Valid @PathVariable Long stockId) {
        return inventoryService.updateStock(stockRequest, stockId);
    }

    //--------------------------------------------------------------------------------------------------------------------

    @PostMapping("/inventories/filter") //inventories/filter?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> filterInventories(
            @RequestBody InventorySearchCriteria searchCriteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.filterInventories(searchCriteria, page, size);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/inventories/search/{criteria}") //inventories/search/criteria?page=0&size=10
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> searchInventories(
            @PathVariable String criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String decodedCriteria = java.net.URLDecoder.decode(criteria, "UTF-8");
            return inventoryService.searchInventories(decodedCriteria, page, size);
        } catch (Exception e) {
            throw new RuntimeException("Invalid search criteria format", e);
        }
    }


    //--------------------------------------------------------------------------------------------------------------------

}
