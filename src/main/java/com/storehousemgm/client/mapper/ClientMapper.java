package com.storehousemgm.client.mapper;

import com.storehousemgm.client.dto.ClientRequest;
import com.storehousemgm.client.dto.ClientResponse;
import com.storehousemgm.client.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client mapClientRequestToClient(ClientRequest clientRequest, Client client){
        client.setBusinessName(clientRequest.getBusinessName());
        client.setEmail(clientRequest.getEmail());
        client.setContactNumber(clientRequest.getContactNumber());
        return client;
    }
    public ClientResponse mapClientToClientResponse(Client client){
        return ClientResponse
                .builder()
                .apiKey(client.getApiKey())
                .build();
    }
}
