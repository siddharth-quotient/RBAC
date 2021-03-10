package com.example.user.service;

import com.example.user.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.user.web.exception.GroupServiceDownException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class ValidateRoleForUserId {

    private final RestTemplate restTemplate;
    private final RoleRestTemplateResponseErrorHandler roleRestTemplateResponseErrorHandler;

    Boolean checkRolePermissionExistForUser(Long userId, Long roleId){

        restTemplate.setErrorHandler(roleRestTemplateResponseErrorHandler);

        try {
            return restTemplate.getForObject("http://group-service/groups/userId/" + userId + "/roleId/" + roleId + "/check",
                    Boolean.class);
        }
        catch (IllegalStateException e) {
            throw new GroupServiceDownException("Group Service Down!");
        }
        catch (RestClientException ex){
            throw new GroupServiceDownException("Group Service acting poorly, timed out!");
        }
    }
}
