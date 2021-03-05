package com.example.user.service;

import com.example.user.web.exception.GroupServiceDownException;
import com.example.user.web.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class CheckRoleByUserIdRestTemplateErrorHandler {

    private final RestTemplate restTemplate;

    Boolean checkRolePermissionExistForUser(Long userId, Long roleId){

        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
                return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
            }

            @Override
            public void handleError(ClientHttpResponse httpResponse) throws IOException {

                if (httpResponse.getStatusCode()
                        .series() == HttpStatus.Series.SERVER_ERROR) {
                    // handle SERVER_ERROR (Group Service Down)
                    throw new GroupServiceDownException("Group Service Down!");
                } else if (httpResponse.getStatusCode()
                        .series() == HttpStatus.Series.CLIENT_ERROR) {
                    // handle CLIENT_ERROR
                    if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RoleNotFoundException("Invalid Role Id: "+ roleId);
                    }
                }
            }
        });


        try {
            return restTemplate.getForObject("http://group-service/groups/userId/" + userId + "/roleId/" + roleId + "/check",
                    Boolean.class);
        }
        catch (IllegalStateException e) {
            //Caught when Group Service is Down - (No way to check Group Validity - pass True)
            throw new GroupServiceDownException("Group Service Down!");
        }
    }
}
