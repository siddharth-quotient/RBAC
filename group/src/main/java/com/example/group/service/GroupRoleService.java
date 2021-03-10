package com.example.group.service;

import com.example.group.web.model.requestDto.GroupRoleMappingRequestDto;
import com.example.group.web.model.requestDto.GroupRoleMappingUpdateRequestDto;
import com.example.group.web.model.responseDto.GroupRoleMappingResponseDto;

import java.util.Set;

/**
 * Interface that provides contract for GroupRole Service
 *
 * @author Siddharth Mehta
 */
public interface GroupRoleService {
    Set<GroupRoleMappingResponseDto> getAllGroupRoleMappings();
    GroupRoleMappingResponseDto getGroupRoleMappingById(Long groupRoleId);
    GroupRoleMappingResponseDto updateGroupRoleMappingById(GroupRoleMappingUpdateRequestDto groupRoleMappingUpdateRequestDto);
    GroupRoleMappingResponseDto createGroupRoleMapping(GroupRoleMappingRequestDto groupRoleMappingRequestDto);
    GroupRoleMappingResponseDto deleteById(Long groupRoleId);
}
