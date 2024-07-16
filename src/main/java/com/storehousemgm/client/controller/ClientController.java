package com.storehousemgm.client.controller;

import com.storehousemgm.client.dto.ClientRequest;
import com.storehousemgm.client.dto.ClientResponse;
import com.storehousemgm.client.service.ClientService;
import com.storehousemgm.utility.ErrorStructure;
import com.storehousemgm.utility.ResponseStructure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Client Endpoints", description = "Contains all the endpoints that are related to the Client entity")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Operation(description = "The endpoint is used to add the Client data to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client Created"),
                    @ApiResponse(responseCode = "400", description = "Invalid Input", content = {
                            @Content(schema = @Schema(oneOf = ErrorStructure.class))
                    })
            })
    @PostMapping("/client/register")
    private ResponseEntity<ResponseStructure<ClientResponse>> addClient(@Valid @RequestBody ClientRequest clientRequest){
      return clientService.addClient(clientRequest);
    }


}
