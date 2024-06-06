package com.storehousemgm.storehouse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StoreHouseController {

	@GetMapping("/storehouses")
	public String getStoreHouse() {
		return "Hello Bablesh";
	}

}
