package com.example.group.service;

import com.example.group.web.model.GroupDto;

import java.util.Set;

public interface GroupService {
    Set<GroupDto> getGroups();
    GroupDto getGroupById(Long groupId);
    GroupDto updateGroupById(Long groupId, GroupDto groupDto);
    GroupDto createGroup(GroupDto groupDto);
    void deleteById(Long groupId);
}
