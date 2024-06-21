package com.storehousemgm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockNotExistException extends RuntimeException {
   private String message;
}
