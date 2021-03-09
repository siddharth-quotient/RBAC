package com.example.group.service;

import com.example.group.web.model.GroupDto;
import com.example.group.web.model.RolesList;
import com.example.group.web.model.UserGroupMappingDto;

import java.util.Set;

/**
 * Interface that provides contract for Group Service
 *
 * @author Siddharth Mehta
 */
public interface GroupService {
    Set<GroupDto> getAllGroups();
    GroupDto getGroupById(Long groupId);
    GroupDto updateGroupById(Long groupId, GroupDto groupDto);
    GroupDto createGroup(GroupDto groupDto);
    void deleteById(Long groupId);

    /*----------------- Roles from Group Id -------------------*/
    RolesList getRolesByGroupId(Long groupId);

    /*----------------- Groups from User Name -------------------*/
    Set<GroupDto> getGroupsByUserId(Set<UserGroupMappingDto> userGroupMappingDtos);
}
