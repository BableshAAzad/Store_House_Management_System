package com.storehousemgm.client.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {
    private Long clientId;
    private String businessName;
    private String email;
    private long contactNumber;
    private String apiKey;
}
