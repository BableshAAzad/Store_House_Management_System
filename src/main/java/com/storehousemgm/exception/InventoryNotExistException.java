package com.storehousemgm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryNotExistException extends RuntimeException{
    private String message;
}
