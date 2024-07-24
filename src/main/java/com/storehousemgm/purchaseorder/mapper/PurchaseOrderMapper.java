package com.storehousemgm.purchaseorder.mapper;

import com.storehousemgm.purchaseorder.dto.OrderRequestDto;
import com.storehousemgm.purchaseorder.dto.OrderResponseDto;
import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderMapper {
    public OrderResponseDto mapPurchaseOrderToOrderResponse(PurchaseOrder purchaseOrder) {
        OrderResponseDto.OrderResponseDtoBuilder responseBuilder = OrderResponseDto.builder()
                .orderId(purchaseOrder.getOrderId())
                .invoiceLink(purchaseOrder.getInvoiceLink())
                .invoiceDate(purchaseOrder.getInvoiceDate());

        if (!purchaseOrder.getInventories().isEmpty()) {
            int lastIndex = purchaseOrder.getInventories().size() - 1;
            responseBuilder
                    .inventoryImage(purchaseOrder.getInventories().get(lastIndex).getProductImage())
                    .inventoryTitle(purchaseOrder.getInventories().get(lastIndex).getProductTitle());
        }

        return responseBuilder.build();
    }
}
