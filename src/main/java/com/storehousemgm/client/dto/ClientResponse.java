package com.storehousemgm.client.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {
    private String apiKey;
    private String username;
    private Long clientId;
}
