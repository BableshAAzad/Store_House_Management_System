package com.storehousemgm.client.service;

import com.storehousemgm.client.dto.ClientRequest;
import com.storehousemgm.client.dto.ClientResponse;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<ResponseStructure<ClientResponse>> addClient(@Valid ClientRequest clientRequest);
}
