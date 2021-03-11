package com.example.group.service;


import com.example.group.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.group.web.exception.RoleServiceDownException;
import com.example.group.web.dto.responseDto.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateRoleForGroupRoleMapping {
    private final RestTemplate restTemplate;
    private final RoleRestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    void checkRoleExist(Long roleId){

        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);

        try {
            RoleResponseDto roleResponseDto = restTemplate.getForObject("http://role-service/roles/" + roleId, RoleResponseDto.class);
        }
        catch (IllegalStateException e) {
            log.error("[checkRoleExist] Role Service Down!");
            throw new RoleServiceDownException("Role Service Down!");
        }
        catch (RestClientException ex){
            log.error("[checkRoleExist] Role Service acting poorly, timed out!");
            throw new RoleServiceDownException("Role Service acting poorly, timed out!");
        }
    }
}
