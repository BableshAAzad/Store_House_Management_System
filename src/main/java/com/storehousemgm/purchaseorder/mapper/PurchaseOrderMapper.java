package com.storehousemgm.purchaseorder.mapper;

import com.storehousemgm.purchaseorder.dto.PurchaseOrderRequest;
import com.storehousemgm.purchaseorder.dto.PurchaseOrderResponse;
import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderMapper {
    public PurchaseOrder mapPurchaseOrderRequestToPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest, PurchaseOrder purchaseOrder){
        purchaseOrder.setOrderQuantity(purchaseOrderRequest.getOrderQuantity());
        purchaseOrder.setCustomerId(purchaseOrderRequest.getCustomerId());
        return purchaseOrder;
    }
    public PurchaseOrderResponse mapPurchaseOrderToPurchaseOrderResponse(PurchaseOrder purchaseOrder){
        return PurchaseOrderResponse.builder()
                .orderId(purchaseOrder.getOrderId())
                .orderQuantity(purchaseOrder.getOrderQuantity())
                .invoiceLink(purchaseOrder.getInvoiceLink())
                .customerId(purchaseOrder.getCustomerId())
                .build();
    }
}
