package com.example.user.service;

import com.example.user.UserApplication;
import com.example.user.restTemplate.GroupRestTemplateResponseErrorHandler;
import com.example.user.web.exception.GroupServiceDownException;
import com.example.user.web.dto.responseDto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ValidateGroupForUserGroupMapping {
    private final RestTemplate restTemplate;

    private final GroupRestTemplateResponseErrorHandler groupRestTemplateResponseErrorHandler;

    void checkGroupExist(Long groupId) {

        restTemplate.setErrorHandler(groupRestTemplateResponseErrorHandler);

        try {
            GroupResponseDto groupResponseDto = restTemplate.getForObject("http://group-service/groups/" + groupId, GroupResponseDto.class);
        }
        catch (IllegalStateException illegalStateException) {
            throw new GroupServiceDownException("Group Service Down!");
        }
        catch (RestClientException ex){
            throw new GroupServiceDownException("Group Service acting poorly, timed out!");
        }
    }
}
