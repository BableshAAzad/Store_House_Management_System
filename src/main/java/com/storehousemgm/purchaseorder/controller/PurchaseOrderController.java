package com.storehousemgm.purchaseorder.controller;

import com.storehousemgm.purchaseorder.dto.OrderRequestDto;
import com.storehousemgm.purchaseorder.dto.OrderResponseDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    @PostMapping("/clients/inventories/{inventoryId}/purchase-orders")
    public ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            @Valid @RequestBody OrderRequestDto orderRequestDto,
            @Valid @PathVariable Long inventoryId) throws IOException {
        return purchaseOrderService.generatePurchaseOrder(orderRequestDto, inventoryId);
    }

    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping("/clients/purchase-orders/invoice/{orderId}")
    public ResponseEntity<byte[]> getOrderInvoice(@PathVariable Long orderId) {
        byte[] pdfData = purchaseOrderService.getPdfData(orderId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "invoice_" + orderId + ".pdf");
        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }


    //--------------------------------------------------------------------------------------------------------------------

    @Operation(description = "The endpoint is used to find the PurchaseOrder data to the database",
            responses = {
                    @ApiResponse(responseCode = "302", description = "PurchaseOrder founded"),
                    @ApiResponse(responseCode = "404", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @GetMapping("/clients/purchaseOrders/{orderId}")
    public ResponseEntity<ResponseStructure<OrderResponseDto>> findPurchaseOrder(@Valid @PathVariable Long orderId) {
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
    @GetMapping("/clients/purchase-orders/customers/{customerId}")
    public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findPurchaseOrders(
           @PathVariable Long customerId) {
        return purchaseOrderService.findPurchaseOrders(customerId);
    }
    //--------------------------------------------------------------------------------------------------------------------

}
