package com.storehousemgm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PurchaseOrderNotCompletedException extends RuntimeException {
   private String message;
}
