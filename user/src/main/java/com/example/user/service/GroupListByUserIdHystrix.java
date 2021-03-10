package com.example.user.service;


import com.example.user.web.model.responseDto.GroupResponseDto;
import com.example.user.web.model.responseDto.GroupsList;
import com.example.user.web.model.responseDto.UserGroupMappingResponseDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Hystrix Circuit Breaker implementation when fetching Groups for a User
 *
 * @author Siddharth Mehta
 */
@Service
@RequiredArgsConstructor
public class GroupListByUserIdHystrix {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackGroupListByUserId")
    public ResponseEntity<GroupsList> getGroupListByUserId(Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos){
        ResponseEntity<GroupsList> groupsListResponseEntity = restTemplate.postForEntity("http://group-service/groups/user-groups/", userGroupMappingResponseDtos, GroupsList.class);
        return groupsListResponseEntity;
    }

    public ResponseEntity<GroupsList> getFallBackGroupListByUserId(Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos){
        GroupsList fallBackGroupList = new GroupsList();
        Set<GroupResponseDto> groupResponseDtoHashSet = new HashSet<>();

        if(!userGroupMappingResponseDtos.isEmpty()){
            userGroupMappingResponseDtos.forEach(userGroupMappingDto -> {
                groupResponseDtoHashSet.add(new GroupResponseDto(userGroupMappingDto.getGroupId(), null, null,
                        "Group Name Unavailable", "Group Description Unavailable"));
            });
        }

        fallBackGroupList.setGroups(groupResponseDtoHashSet);
        return new ResponseEntity<>(fallBackGroupList, HttpStatus.OK);
    }
}
