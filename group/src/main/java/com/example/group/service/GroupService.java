package com.example.group.service;

import com.example.group.web.model.GroupDto;
import com.example.group.web.model.RoleDto;
import com.example.group.web.model.RolesList;

import java.util.Set;

public interface GroupService {
    Set<GroupDto> getGroups();
    GroupDto getGroupById(Long groupId);
    GroupDto updateGroupById(Long groupId, GroupDto groupDto);
    GroupDto createGroup(GroupDto groupDto);
    void deleteById(Long groupId);

    /*----------------- Roles from Group Ids -------------------*/
    RolesList getRolesByGroupId(Long groupId);
}
