package com.storehousemgm.inventory.dto;

import com.storehousemgm.enums.MaterialType;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.util.List;

@Getter
public class InventoryRequest {

    @NotNull(message = "Product title cannot be null")
    @NotBlank(message = "Product title cannot be blank")
    @Size(max = 255, message = "Product title must be less than or equal to 255 characters")
    private String productTitle;

    @Min(value = 0, message = "Length in meters must be non-negative")
    private double lengthInMeters;

    @Min(value = 0, message = "Breadth in meters must be non-negative")
    private double breadthInMeters;

    @Min(value = 0, message = "Height in meters must be non-negative")
    private double heightInMeters;

    @Min(value = 0, message = "Weight in kg must be non-negative")
    private double weightInKg;

    @Min(value = 0, message = "price can not be null")
    private double price;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private String productImage;

    @NotNull(message = "Material types cannot be null")
    @NotEmpty(message = "Material types cannot be empty")
    private List<MaterialType> materialTypes;

    @NotNull(message = "Seller ID cannot be null")
    private Long sellerId;
}
