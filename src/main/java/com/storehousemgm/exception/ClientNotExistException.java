package com.storehousemgm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ClientNotExistException extends RuntimeException {
    private String message;
}
