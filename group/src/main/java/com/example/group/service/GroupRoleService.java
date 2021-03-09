package com.example.group.service;

import com.example.group.web.model.GroupRoleMappingDto;

import java.util.Set;

/**
 * Interface that provides contract for GroupRole Service
 *
 * @author Siddharth Mehta
 */
public interface GroupRoleService {
    Set<GroupRoleMappingDto> getAllGroupRoleMapping();
    GroupRoleMappingDto getGroupRoleMappingById(Long groupRoleId);
    GroupRoleMappingDto updateGroupRoleMappingById(Long groupRoleId, GroupRoleMappingDto groupRoleMappingDtoDto);
    GroupRoleMappingDto createGroupRoleMapping(GroupRoleMappingDto groupRoleMappingDto);
    void deleteById(Long groupRoleId);
}
