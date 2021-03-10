package com.example.group.service;

import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.dto.responseDto.GroupRoleMappingResponseDto;
import com.example.group.web.dto.responseDto.RoleResponseDto;
import com.example.group.web.dto.responseDto.RolesList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Hystrix Circuit Breaker implementation when fetching Roles for a Group
 *
 * @author Siddharth Mehta
 */
@Service
@RequiredArgsConstructor
public class RoleListByGroupIdHystrix {

    private final RestTemplate restTemplate;
    private final GroupRoleRepository groupRoleRepository;

    @HystrixCommand(fallbackMethod = "getFallBackRoleListByGroupId")
    public ResponseEntity<RolesList> getRoleListByGroupId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos){
        ResponseEntity<RolesList> rolesListResponseEntity = restTemplate.postForEntity("http://role-service/roles/group-roles/", groupRoleMappingResponseDtos, RolesList.class);
        return rolesListResponseEntity;
    }

    public ResponseEntity<RolesList> getFallBackRoleListByGroupId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos){
        RolesList fallBackRolesList = new RolesList();
        Set<RoleResponseDto> roleResponseDtoHashSet = new HashSet<>();

        if(!groupRoleMappingResponseDtos.isEmpty()){
            groupRoleMappingResponseDtos.forEach(groupRoleMappingDto -> {
                roleResponseDtoHashSet.add(new RoleResponseDto(groupRoleMappingDto.getRoleId(), null, null,
                        "Role Name Unavailable", "Role Description Unavailable"));
            });
        }

        fallBackRolesList.setRoles(roleResponseDtoHashSet);
        return new ResponseEntity<>(fallBackRolesList, HttpStatus.OK);
    }
}
