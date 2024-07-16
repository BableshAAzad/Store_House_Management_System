package com.storehousemgm.utility;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {
    //     http;//localhost:8080/swagger-ui.html
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("StoreHouse_Management_System")
                .version("v1")
                .description(
                        "Spring boot project built using **RESTful** Architecture covers all the `basic CRUD operations`\n"
                                +"#### Features : \n"
                                + "- Covers all CRUD operations\n"
                                + "- Performed field validations\n"
                                + "- used DTOs to control inbound and outbound data\n"));
    }
}
