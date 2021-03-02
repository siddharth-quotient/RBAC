package com.example.user.service;

import com.example.user.repository.UserGroupRepository;
import com.example.user.web.exception.UserGroupNotFoundException;
import com.example.user.web.model.GroupDto;
import com.example.user.web.model.GroupsList;
import com.example.user.web.model.UserGroupMappingDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupHystrix {

    private final RestTemplate restTemplate;
    private final UserGroupRepository userGroupRepository;

    @HystrixCommand(fallbackMethod = "getFallBackGroupListByUserId")
    public ResponseEntity<GroupsList> getGroupListByUserId(Set<UserGroupMappingDto> userGroupMappingDtos){
        ResponseEntity<GroupsList> groupsListResponseEntity = restTemplate.postForEntity("http://group-service/groups/user-groups/", userGroupMappingDtos, GroupsList.class);
        return groupsListResponseEntity;
    }

    public ResponseEntity<GroupsList> getFallBackGroupListByUserId(Set<UserGroupMappingDto> userGroupMappingDtos){
        if(userGroupMappingDtos.isEmpty()){
            throw new UserGroupNotFoundException("Empty User-Group Mapping Set in getFallBackGroupListByUserId");
        }

        Long userId = userGroupMappingDtos.stream().findAny().get().getUserId();

        GroupsList fallBackGroupList = new GroupsList();
        Set<GroupDto> groupDtoHashSet = new HashSet<>();

        userGroupRepository.findByUserId(userId).forEach(userGroupMapping -> {
            groupDtoHashSet.add(new GroupDto(userGroupMapping.getGroupId(), null, null,
                    "Group Name Unavailable", "Group Description Unavailable"));
        });

        fallBackGroupList.setGroupDtoSet(groupDtoHashSet);
        return new ResponseEntity<>(fallBackGroupList, HttpStatus.OK);
    }
}
