package com.example.group.service;

import com.example.group.web.model.requestDto.GroupRequestDto;
import com.example.group.web.model.requestDto.GroupUpdateRequestDto;
import com.example.group.web.model.responseDto.GroupResponseDto;
import com.example.group.web.model.responseDto.RolesList;
import com.example.group.web.model.responseDto.UserGroupMappingResponseDto;

import java.util.Set;

/**
 * Interface that provides contract for Group Service
 *
 * @author Siddharth Mehta
 */
public interface GroupService {
    Set<GroupResponseDto> getAllGroups();
    GroupResponseDto getGroupById(Long groupId);
    GroupResponseDto updateGroupById(GroupUpdateRequestDto groupUpdateRequestDto);
    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);
    GroupResponseDto deleteById(Long groupId);

    /*----------------- Roles from Group Id -------------------*/
    RolesList getRolesByGroupId(Long groupId);

    /*----------------- Groups from User Name -------------------*/
    Set<GroupResponseDto> getGroupsByUserId(Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos);
}
