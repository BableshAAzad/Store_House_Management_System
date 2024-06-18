package com.storehousemgm.storagetype.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class StorageTypeRequest {

    @NotNull(message = "Length cannot be null")
    @Positive(message = "Length must be positive")
    private double lengthInMeters;

    @NotNull(message = "Breadth cannot be null")
    @Positive(message = "Breadth must be positive")
    private double breadthInMeters;

    @NotNull(message = "Height cannot be null")
    @Positive(message = "Height must be positive")
    private double heightInMeters;

    @NotNull(message = "Capacity Weight cannot be null")
    @Positive(message = "Capacity Weight must be positive")
    private double capacityWeightInKg;

}
