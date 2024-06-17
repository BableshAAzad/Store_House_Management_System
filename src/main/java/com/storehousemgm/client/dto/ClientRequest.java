package com.storehousemgm.client.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ClientRequest {

    @NotNull(message = "BusinessName can not be null")
    @NotBlank(message = "BusinessName can not be blank")
    private String businessName;

    @NotBlank(message = "Email can not be blank")
    @NotNull(message = "Email can not be null")
    @Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
    private String email;

    @Min(value = 6000000000L, message = "Invalid phone number")
    @Max(value = 9999999999L, message = "Invalid phone number")
    @NotNull(message = "Contact number can not be null")
    private long contactNumber;
}
