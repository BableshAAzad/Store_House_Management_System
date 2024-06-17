package com.storehousemgm.client.controller;

import com.storehousemgm.client.dto.ClientRequest;
import com.storehousemgm.client.dto.ClientResponse;
import com.storehousemgm.client.service.ClientService;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/client/register")
    private ResponseEntity<ResponseStructure<ClientResponse>> addClient(@Valid @RequestBody ClientRequest clientRequest){
      return clientService.addClient(clientRequest);
    }


}
