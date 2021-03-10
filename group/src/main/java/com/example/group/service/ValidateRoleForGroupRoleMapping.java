package com.example.group.service;


import com.example.group.restTemplate.RoleRestTemplateResponseErrorHandler;
import com.example.group.web.exception.RoleServiceDownException;
import com.example.group.web.dto.responseDto.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
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
            //Caught when Role Service is Down
            throw new RoleServiceDownException("Role Service Down!");
        }
    }
}
