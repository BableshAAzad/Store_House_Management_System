package com.storehousemgm.client.service.impl;

import com.storehousemgm.client.dto.ClientRequest;
import com.storehousemgm.client.dto.ClientResponse;
import com.storehousemgm.client.entity.Client;
import com.storehousemgm.client.mapper.ClientMapper;
import com.storehousemgm.client.repository.ClientRepository;
import com.storehousemgm.client.service.ClientService;
import com.storehousemgm.exception.ClientAlreadyExistException;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ResponseEntity<ResponseStructure<ClientResponse>> addClient(ClientRequest clientRequest) {
        if(!clientRepository.existsByEmail(clientRequest.getEmail())) {
            Client client = clientMapper.mapClientRequestToClient(clientRequest, new Client());
            client.setApiKey(UUID.randomUUID().toString());
            client = clientRepository.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<ClientResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Client Created")
                    .setData(clientMapper.mapClientToClientResponse(client)));
        }else
            throw new ClientAlreadyExistException("EmailId : "+clientRequest.getEmail()+", is already exist");
    }
}
