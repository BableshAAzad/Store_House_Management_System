package com.storehousemgm.filter;

import com.storehousemgm.client.entity.Client;
import com.storehousemgm.client.repository.ClientRepository;
import com.storehousemgm.exception.ClientNotExistException;
import com.storehousemgm.exception.IllegalOperationException;
import com.storehousemgm.exception.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ClientApiKeyFilter extends OncePerRequestFilter {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getSession(false) != null)
            throw new IllegalOperationException("Please logout first....!!!");

        if (!request.getRequestURI().equals("/api/v1/clients/register")) {
            String apiKey = request.getHeader("API-KEY");
            String username = request.getHeader("USERNAME");

            if (apiKey != null || username != null) {
                Client client = clientRepository.findByEmail(username)
                        .orElseThrow(() -> new ClientNotExistException("Email: " + username + ", does not exist"));
                if (!apiKey.equals(client.getApiKey())) {
                    throw new BadCredentialsException("Invalid Credential");
                }
            } else
                throw new UserNotFoundException("User not found");
        }
        filterChain.doFilter(request, response);
    }
}
