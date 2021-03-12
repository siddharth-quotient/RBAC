package com.example.user.service;

import com.example.user.restTemplate.GroupRestTemplateResponseErrorHandler;
import com.example.user.web.exception.GroupNotFoundException;
import com.example.user.web.exception.GroupServiceDownException;
import com.example.user.web.dto.responseDto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateGroupForUserGroupMapping {
    private final RestTemplate restTemplate;

    private final GroupRestTemplateResponseErrorHandler groupRestTemplateResponseErrorHandler;

    void checkGroupExist(Long groupId) {

        restTemplate.setErrorHandler(groupRestTemplateResponseErrorHandler);

        try {
            GroupResponseDto groupResponseDto = restTemplate.getForObject("http://group-service/groups/" + groupId, GroupResponseDto.class);
            if(groupResponseDto.getGroupId()==null){
                log.error("[checkGroupExist] Group does not exist!");
                throw new GroupNotFoundException("Invalid Group Id: "+groupId);
            }
        }

        catch (IllegalStateException illegalStateException) {
            log.error("[checkGroupExist] Group Service Down!");
            throw new GroupServiceDownException("Group Service Down!");
        }
        catch (RestClientException restClientException){
            log.error("[checkGroupExist] Group Service acting poorly, timed out!");
            throw new GroupServiceDownException("Group Service acting poorly, timed out!");
        }
    }
}
