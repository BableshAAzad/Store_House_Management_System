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

    @NotNull(message = "Material types cannot be null")
    @Size(min=1, message = "There must be at least one materials type")
    private List<MaterialType> materialTypes;

}
