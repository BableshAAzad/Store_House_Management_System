package com.storehousemgm.purchaseorder.controller;

import com.storehousemgm.purchaseorder.dto.PurchaseOrderRequest;
import com.storehousemgm.purchaseorder.dto.PurchaseOrderResponse;
import com.storehousemgm.purchaseorder.service.PurchaseOrderService;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    //--------------------------------------------------------------------------------------------------------------------

    @PostMapping("/inventories/{inventoryId}/purchaseOrders")
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> addPurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequest purchaseOrderRequest,
            @Valid @PathVariable Long inventoryId) {
        return purchaseOrderService.addPurchaseOrder(purchaseOrderRequest, inventoryId);
    }
    //--------------------------------------------------------------------------------------------------------------------

//    note : this method is only for demo purpose
    @PutMapping("/purchaseOrders/{orderId}")
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> updatePurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequest purchaseOrderRequest,
            @Valid @PathVariable Long orderId) {
        return purchaseOrderService.updatePurchaseOrder(purchaseOrderRequest, orderId);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @GetMapping("/purchaseOrders/{orderId}")
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> findPurchaseOrder(@Valid @PathVariable Long orderId){
        return purchaseOrderService.findPurchaseOrder(orderId);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @GetMapping("/purchaseOrders")
    public ResponseEntity<ResponseStructure<List<PurchaseOrderResponse>>> findPurchaseOrders(){
        return purchaseOrderService.findPurchaseOrders();
    }
    //--------------------------------------------------------------------------------------------------------------------

}
