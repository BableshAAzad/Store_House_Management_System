package com.storehousemgm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PurchaseOrderNotExistException extends RuntimeException {
    private String message;
}
