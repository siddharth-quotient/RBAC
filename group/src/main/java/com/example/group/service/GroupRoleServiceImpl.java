package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.domain.GroupRoleMapping;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.exception.GroupRoleNotFoundException;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.model.GroupRoleMappingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupRoleServiceImpl implements GroupRoleService {

    private final GroupRoleRepository groupRoleRepository;
    private final GroupRepository groupRepository;
    private final GroupRoleMapper groupRoleMapper;

    @Override
    public GroupRoleMappingDto createGroupRole(GroupRoleMappingDto groupRoleMappingDto) {

        if(groupRoleMappingDto == null){
            throw new GroupRoleNotFoundException("Group and Role Mapping cannot be Null");
        }

        Long groupId = groupRoleMappingDto.getGroupId();

        /* Check for valid group_id */
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
        }

        //todo How to implement validation for role id?

        return groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleRepository.save(groupRoleMapper.groupRoleMappingDtoToGroupRoleMapping(groupRoleMappingDto)));
    }
}
