package com.storehousemgm.inventory.controller;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.service.InventoryService;
import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    //--------------------------------------------------------------------------------------------------------------------

    @PostMapping("/clients/{clientId}/storages/{storageId}/inventories")
    public ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            @Valid @RequestBody InventoryRequest inventoryRequest,
            @Valid @PathVariable Long storageId,
            @Valid @RequestParam int quantity,
            @Valid @PathVariable Long clientId ) {
        return inventoryService.addInventory(inventoryRequest, storageId, clientId, quantity);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @PutMapping("/inventories/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(
            @Valid @RequestBody InventoryRequest inventoryRequest,
            @Valid @PathVariable Long inventoryId) {
        return inventoryService.updateInventory(inventoryRequest, inventoryId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/inventories/{inventoryId}")
    public ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(
            @Valid @PathVariable Long inventoryId) {
        return inventoryService.findInventory(inventoryId);
    }
    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/inventories")
    public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findInventories(){
        return inventoryService.findInventories();
    }

    //--------------------------------------------------------------------------------------------------------------------

    @PutMapping("/stocks/{stockId}")
    public ResponseEntity<ResponseStructure<StockResponse>> updateStock(
            @Valid @RequestBody StockRequest stockRequest,
            @Valid @PathVariable Long stockId){
        return inventoryService.updateStock(stockRequest, stockId);
    }

}
