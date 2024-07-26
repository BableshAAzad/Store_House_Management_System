package com.storehousemgm.purchaseorder.service;

import com.storehousemgm.purchaseorder.dto.OrderRequestDto;
import com.storehousemgm.purchaseorder.dto.OrderResponseDto;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PurchaseOrderService {

    ResponseEntity<ResponseStructure<OrderResponseDto>> findPurchaseOrder(@Valid Long orderId);

    ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findPurchaseOrders(Long customerId);

    ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(
            OrderRequestDto orderRequestDto, Long inventoryId) throws IOException;

    byte[] getPdfData(Long orderId);
}
