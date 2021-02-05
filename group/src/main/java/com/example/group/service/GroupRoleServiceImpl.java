package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.domain.GroupRoleMapping;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.exception.GroupRoleNotFoundException;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.model.GroupDto;
import com.example.group.web.model.GroupRoleMappingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupRoleServiceImpl implements GroupRoleService {

    private final GroupRoleRepository groupRoleRepository;
    private final GroupRepository groupRepository;
    private final GroupRoleMapper groupRoleMapper;

    @Override
    public Set<GroupRoleMappingDto> getGroupRoleMapping() {
        Set<GroupRoleMappingDto> groupRoles = new HashSet<>();

        groupRoleRepository.findAll().forEach(groupRoleMapping -> {
            groupRoles.add(groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleMapping));
        });
        return groupRoles;
    }

    @Override
    public GroupRoleMappingDto getGroupRoleMappingById(Long groupRoleId) {
        if(groupRoleId==null){
            throw new GroupRoleNotFoundException("Group-Role Mapping cannot be Null");
        }

        Optional<GroupRoleMapping> groupRoleOptional = groupRoleRepository.findById(groupRoleId);
        if(groupRoleOptional.isPresent()){
            return groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleOptional.get());
        }
        throw new GroupRoleNotFoundException("Invalid Group-Role Mapping with Id :"+ groupRoleId);
    }

    @Override
    public GroupRoleMappingDto updateGroupRoleMappingById(Long groupRoleId, GroupRoleMappingDto groupRoleMappingDto) {
        if(groupRoleId==null){
            throw new GroupRoleNotFoundException("Group-Role Mapping cannot be Null");
        }

        Optional<GroupRoleMapping> groupRoleOptional = groupRoleRepository.findById(groupRoleId);

        if(groupRoleOptional.isPresent()){
            GroupRoleMapping groupRoleMapping = groupRoleOptional.get();

            Long groupId = groupRoleMappingDto.getGroupId();

            /* Check for valid group_id */
            if(!validateGroupId(groupId)){
                throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
            }

            //todo How to implement validation for role id?

            groupRoleMapping.setGroupId( groupRoleMappingDto.getGroupId() );
            groupRoleMapping.setRoleId( groupRoleMappingDto.getRoleId() );

            return groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleRepository.save(groupRoleMapping));
        }

        throw new GroupRoleNotFoundException("Invalid Group-Role Mapping with Id :"+ groupRoleId);
    }

    @Override
    public GroupRoleMappingDto createGroupRoleMapping(GroupRoleMappingDto groupRoleMappingDto) {

        if(groupRoleMappingDto == null){
            throw new GroupRoleNotFoundException("Group-Role Mapping cannot be Null");
        }

        Long groupId = groupRoleMappingDto.getGroupId();

        /* Check for valid group_id */
        if(!validateGroupId(groupId)){
            throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
        }

        //todo How to implement validation for role id?

        return groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleRepository.save(groupRoleMapper.groupRoleMappingDtoToGroupRoleMapping(groupRoleMappingDto)));
    }

    @Override
    public void deleteById(Long groupRoleId) {
        if(groupRoleId==null){
            throw new GroupRoleNotFoundException("Group-Role Mapping cannot be Null");
        }

        Optional<GroupRoleMapping> groupRoleOptional = groupRoleRepository.findById(groupRoleId);

        if(!groupRoleOptional.isPresent()){
            throw new GroupRoleNotFoundException("Invalid Group-Role Mapping with Id :"+ groupRoleId);
        }

        groupRoleRepository.deleteById(groupRoleId);
    }


    public boolean validateGroupId(Long groupId){
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            return false;
        }
        return true;
    }
}
