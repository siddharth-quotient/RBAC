package com.example.user.service;

import com.example.user.web.model.UserGroupMappingDto;

import java.util.Set;

/**
 * Interface that provides contract for UserGroup Service
 *
 * @author Siddharth Mehta
 */
public interface UserGroupService {
    Set<UserGroupMappingDto> getAllUserGroupMappings();
    UserGroupMappingDto getUserGroupMappingById(Long userGroupId);
    UserGroupMappingDto updateUserGroupMappingById(Long userGroupId, UserGroupMappingDto userGroupMappingDto);
    UserGroupMappingDto createUserGroupMapping(UserGroupMappingDto userGroupMappingDto);
    UserGroupMappingDto deleteById(Long userGroupId);
}
