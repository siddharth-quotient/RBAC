package com.example.group.web.mapper;

import com.example.group.domain.GroupRoleMapping;
import com.example.group.web.model.requestDto.GroupRoleMappingRequestDto;
import com.example.group.web.model.responseDto.GroupRoleMappingResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface GroupRoleMapper {
    GroupRoleMapping groupRoleMappingRequestDtoToGroupRoleMapping(GroupRoleMappingRequestDto groupRoleMappingRequestDto);

    GroupRoleMappingResponseDto groupRoleMappingToGroupRoleResponseMappingDto(GroupRoleMapping groupRoleMapping);
}
