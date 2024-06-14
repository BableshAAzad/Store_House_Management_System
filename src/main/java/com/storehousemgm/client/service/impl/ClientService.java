package com.storehousemgm.client.service.impl;

import com.storehousemgm.client.dto.ClientRequest;
import com.storehousemgm.utility.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<ResponseStructure<String>> addClient(@Valid ClientRequest clientRequest);
}
