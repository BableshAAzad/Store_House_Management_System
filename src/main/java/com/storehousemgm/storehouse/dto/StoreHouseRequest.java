package com.storehousemgm.storehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class StoreHouseRequest {
		
	@NotNull(message = "StoreHouse name can not be null")
	@NotBlank(message = "StoreHouse name can not be blank")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters.")
	private String name;

}
