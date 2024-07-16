package com.storehousemgm.purchaseorder.service;

import com.storehousemgm.purchaseorder.dto.PurchaseOrderRequest;
import com.storehousemgm.purchaseorder.dto.PurchaseOrderResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseOrderService {

    ResponseEntity<ResponseStructure<PurchaseOrderResponse>> addPurchaseOrder(
            @Valid PurchaseOrderRequest purchaseOrderRequest, @Valid Long inventoryId);

//    note : this method is only for demo purpose
    ResponseEntity<ResponseStructure<PurchaseOrderResponse>> updatePurchaseOrder(
            @Valid PurchaseOrderRequest purchaseOrderRequest, @Valid Long orderId);

    ResponseEntity<ResponseStructure<PurchaseOrderResponse>> findPurchaseOrder(@Valid Long orderId);

    ResponseEntity<ResponseStructure<List<PurchaseOrderResponse>>> findPurchaseOrders();
}
