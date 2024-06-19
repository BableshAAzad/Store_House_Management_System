package com.storehousemgm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuperAdminAlreadyExistException extends RuntimeException {
    private String message;
}
