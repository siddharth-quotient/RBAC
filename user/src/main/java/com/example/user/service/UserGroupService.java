package com.example.user.service;

import com.example.user.web.model.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.model.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.model.responseDto.UserGroupMappingResponseDto;

import java.util.Set;

/**
 * Interface that provides contract for UserGroup Service
 *
 * @author Siddharth Mehta
 */
public interface UserGroupService {
    Set<UserGroupMappingResponseDto> getAllUserGroupMappings();
    UserGroupMappingResponseDto getUserGroupMappingById(String userGroupStringId);
    UserGroupMappingResponseDto updateUserGroupMappingById(UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDto);
    UserGroupMappingResponseDto createUserGroupMapping(UserGroupMappingRequestDto userGroupMappingRequestDto);
    UserGroupMappingResponseDto deleteById(String userGroupStringId);
}
