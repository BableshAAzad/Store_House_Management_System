package com.storehousemgm.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
    //--------------------------------------------------------------------------------------------------------------------
    @GetMapping
    public String test(){
        return "Welcome To Store House Management System Application";
    }
    //--------------------------------------------------------------------------------------------------------------------
}
