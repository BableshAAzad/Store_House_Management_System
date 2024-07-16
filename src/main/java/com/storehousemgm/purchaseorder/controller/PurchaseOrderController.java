package com.storehousemgm.purchaseorder.controller;

import com.storehousemgm.purchaseorder.dto.PurchaseOrderRequest;
import com.storehousemgm.purchaseorder.dto.PurchaseOrderResponse;
import com.storehousemgm.purchaseorder.service.PurchaseOrderService;
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
@Tag(name = "PurchaseOrder Endpoints", description = "Contains all the endpoints that are related to the PurchaseOrder entity")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to add the Client data to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client Created"),
                    @ApiResponse(responseCode = "400", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PostMapping("/inventories/{inventoryId}/purchaseOrders")
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> addPurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequest purchaseOrderRequest,
            @Valid @PathVariable Long inventoryId) {
        return purchaseOrderService.addPurchaseOrder(purchaseOrderRequest, inventoryId);
    }
    //--------------------------------------------------------------------------------------------------------------------

//    note : this method is only for demo purpose
@Operation(description = "The endpoint is used to update the PurchaseOrder data to the database",
        responses = {
                @ApiResponse(responseCode = "200", description = "PurchaseOrder updated"),
                @ApiResponse(responseCode = "404", description = "Invalid Id", content = {
                        @Content(schema = @Schema(oneOf = ErrorStructure.class))
                })
        })
    @PutMapping("/purchaseOrders/{orderId}")
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> updatePurchaseOrder(
            @Valid @RequestBody PurchaseOrderRequest purchaseOrderRequest,
            @Valid @PathVariable Long orderId) {
        return purchaseOrderService.updatePurchaseOrder(purchaseOrderRequest, orderId);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the PurchaseOrder data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "PurchaseOrder founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/purchaseOrders/{orderId}")
    public ResponseEntity<ResponseStructure<PurchaseOrderResponse>> findPurchaseOrder(@Valid @PathVariable Long orderId){
        return purchaseOrderService.findPurchaseOrder(orderId);
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the PurchaseOrder data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "PurchaseOrder founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/purchaseOrders")
    public ResponseEntity<ResponseStructure<List<PurchaseOrderResponse>>> findPurchaseOrders(){
        return purchaseOrderService.findPurchaseOrders();
    }
    //--------------------------------------------------------------------------------------------------------------------

}
