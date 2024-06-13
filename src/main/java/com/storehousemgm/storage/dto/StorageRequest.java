package com.storehousemgm.storage.dto;

import com.storehousemgm.enums.MaterialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class StorageRequest {
    @NotNull(message = "Block name cannot be null")
    @NotBlank(message = "Block name cannot be blank")
    private String blockName;

    @NotNull(message = "Section name cannot be null")
    @NotBlank(message = "Section name cannot be blank")
    private String section;

    @NotNull(message = "Capacity Weight cannot be null")
    @Positive(message = "Capacity Weight must be positive")
    private Double capacityWeightInKg;

    @NotNull(message = "Length cannot be null")
    @Positive(message = "Length must be positive")
    private Double lengthInMeters;

    @NotNull(message = "Breadth cannot be null")
    @Positive(message = "Breadth must be positive")
    private Double breadthInMeters;

    @NotNull(message = "Height cannot be null")
    @Positive(message = "Height must be positive")
    private Double heightInMeters;

    @NotNull(message = "Material types cannot be null")
    @Size(min=1, message = "There must be at least one materials type")
    private List<MaterialType> materialTypes;

}
