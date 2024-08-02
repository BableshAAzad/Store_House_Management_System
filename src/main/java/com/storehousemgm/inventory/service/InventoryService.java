package com.storehousemgm.inventory.service;

import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.dto.InventorySearchCriteria;
import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface InventoryService {
    ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            @Valid InventoryRequest inventoryRequest, @Valid Long storageId, @Valid Long clientId, @Valid int quantity);

    ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(
            @Valid InventoryRequest inventoryRequest, @Valid Long inventoryId);

    ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(@Valid Long inventoryId);

    ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventories(int page, int size);

    ResponseEntity<ResponseStructure<StockResponse>> updateStock(@Valid StockRequest stockRequest, @Valid Long stockId);

    ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventoriesBySellerId(
            Long sellerId, int page, int size);

    ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> filterInventories(
            InventorySearchCriteria searchCriteria, int page, int size);

    ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> searchInventories(
            String decodedCriteria, int page, int size);
}
