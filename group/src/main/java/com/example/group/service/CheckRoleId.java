package com.example.group.service;

import com.example.group.web.exception.RoleNotFoundException;
import com.example.group.web.model.RoleDto;
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
public class CheckRoleId {

    private final RestTemplate restTemplate;

    Boolean checkRoleExist(Long roleId){

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
                    // handle SERVER_ERROR (Role Service Down)
                    checkFallbackRoleExist();
                } else if (httpResponse.getStatusCode()
                        .series() == HttpStatus.Series.CLIENT_ERROR) {
                    // handle CLIENT_ERROR
                    if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new RoleNotFoundException("Invalid Role Id: "+ roleId);
                    }
                }
            }
        });

        RoleDto roleDto =null;
        try {
            roleDto = restTemplate.getForObject("http://role-service/roles/" + roleId, RoleDto.class);
        }
        catch (IllegalStateException e) {
            //Caught when Role Service is Down - (No way to check Role Validity - pass True)
            return checkFallbackRoleExist();
        }
        return (!(roleDto==null));
    }

    Boolean checkFallbackRoleExist(){
        return true;
    }
}
