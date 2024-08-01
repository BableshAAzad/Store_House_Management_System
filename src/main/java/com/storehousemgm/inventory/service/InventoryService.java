package com.storehousemgm.inventory.service;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.dto.InventorySearchCriteria;
import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            @Valid InventoryRequest inventoryRequest, @Valid Long storageId, @Valid Long clientId, @Valid int quantity);

    ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(
            @Valid InventoryRequest inventoryRequest, @Valid Long inventoryId);

    ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(@Valid Long inventoryId);

    ResponseEntity<ResponseStructure<List<InventoryResponse>>> findInventories();

    ResponseEntity<ResponseStructure<StockResponse>> updateStock(@Valid StockRequest stockRequest, @Valid Long stockId);

    ResponseEntity<ResponseStructure<List<InventoryResponse>>> findInventoriesBySellerId(Long sellerId);

    ResponseEntity<ResponseStructure<List<InventoryResponse>>> filterInventories(InventorySearchCriteria searchCriteria);

    ResponseEntity<ResponseStructure<List<InventoryResponse>>> searchInventories(String decodedCriteria);
}
