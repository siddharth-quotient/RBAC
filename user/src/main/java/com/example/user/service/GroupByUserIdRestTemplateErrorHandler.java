package com.example.user.service;

import com.example.user.domain.GroupDto;
import com.example.user.web.exception.GroupNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class GroupByUserIdRestTemplateErrorHandler {

    private final RestTemplate restTemplate;

    Boolean checkGroupExist(Long groupId){

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
                    checkFallbackGroupExist();
                } else if (httpResponse.getStatusCode()
                        .series() == HttpStatus.Series.CLIENT_ERROR) {
                    // handle CLIENT_ERROR
                    if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                        throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
                    }
                }
            }
        });

        GroupDto groupDto =null;
        try {
            groupDto = restTemplate.getForObject("http://group-service/groups/" + groupId, GroupDto.class);
        }
        catch (IllegalStateException e) {
            //Caught when Group Service is Down - (No way to check Group Validity - pass True)
            return checkFallbackGroupExist();
        }

        return (!(groupDto==null));


    }

    Boolean checkFallbackGroupExist(){
        return true;
    }
}
