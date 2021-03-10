package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.repository.UserGroupRepository;
import com.example.group.web.exception.GroupNameNotUniqueException;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.mapper.GroupMapper;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.dto.requestDto.GroupRequestDto;
import com.example.group.web.dto.requestDto.GroupUpdateRequestDto;
import com.example.group.web.dto.responseDto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for Group Service
 *
 * @author Siddharth Mehta
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupMapper groupMapper;
    private final GroupRoleMapper groupRoleMapper;
    private final RoleListByGroupIdHystrix roleListByGroupIdHystrix;


    @Override
    public AllGroupsResponseDto getAllGroups() {
        AllGroupsResponseDto allGroupsResponseDto = new AllGroupsResponseDto();
        Set<GroupResponseDto> groups = new HashSet<>();

        groupRepository.findAll().forEach(group -> {
            groups.add(groupMapper.groupToGroupResponseDto(group));
        });

        allGroupsResponseDto.setGroups(groups);
        return allGroupsResponseDto;

    }

    @Override
    public GroupResponseDto getGroupById(Long groupId) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isPresent()){
            return groupMapper.groupToGroupResponseDto(groupOptional.get());
        }
        log.error("Invalid Group Id provided while using getGroupById: "+ groupId);
        throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
    }

    @Override
    @Transactional
    public GroupResponseDto updateGroupById(GroupUpdateRequestDto groupUpdateRequestDto) {
        Long groupId = groupUpdateRequestDto.getGroupId();
        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if(groupOptional.isPresent()){
            Group group = groupOptional.get();

            /*Check if group with given groupName already exists*/
            String dtoGroupName = groupUpdateRequestDto.getGroupName();
            Optional<Group> dtoGroupOptional = groupRepository.findByGroupName(dtoGroupName);

            if(dtoGroupOptional.isPresent()){
                Group dtoGroup = dtoGroupOptional.get();
                if(dtoGroup.getGroupId()!=group.getGroupId()){
                    throw new GroupNameNotUniqueException("Group by the name " + dtoGroupName +" already exists!");
                }
            }
            group.setGroupName(groupUpdateRequestDto.getGroupName());
            group.setGroupDescription(groupUpdateRequestDto.getGroupDescription());

            return groupMapper.groupToGroupResponseDto(groupRepository.save(group));

        }
        log.error("Invalid Group Id provided while using updateGroupById: "+ groupId);
        throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
    }

    @Override
    @Transactional
    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {

        try {
            return groupMapper.groupToGroupResponseDto(groupRepository.save(groupMapper.groupRequestDtoToGroup(groupRequestDto)));
        }
        catch (DataIntegrityViolationException ex){
            log.error("Group by the name " + groupRequestDto.getGroupName() +" already exists!");
            throw new GroupNameNotUniqueException("Group by the name " + groupRequestDto.getGroupName() +" already exists!");
        }
    }

    @Override
    @Transactional
    public GroupResponseDto deleteById(Long groupId) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using deleteById: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
        }

        groupRepository.deleteById(groupId);

        /*Delete all User-Group Mappings and Group-Role Mappings for the deleted groupId */
        userGroupRepository.deleteByGroupId(groupId);
        groupRoleRepository.deleteByGroupId(groupId);

        return groupMapper.groupToGroupResponseDto(groupOptional.get());
    }

    /*----------------- Roles from Group Id -------------------*/
    @Override
    @Transactional
    public RolesList getRolesByGroupId(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using getRolesByGroupId: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
        }
        Group group = groupOptional.get();

        Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos = new HashSet<>();

        groupRoleRepository.findByGroupId(groupId).forEach(userGroupMappingDto -> {
            groupRoleMappingResponseDtos.add(groupRoleMapper.groupRoleMappingToGroupRoleResponseMappingDto(userGroupMappingDto));
        });

        ResponseEntity<RolesList>  rolesListResponseEntity = roleListByGroupIdHystrix.getRoleListByGroupId(groupRoleMappingResponseDtos);
        RolesList rolesList = rolesListResponseEntity.getBody();
        rolesList.setGroup(groupMapper.groupToGroupResponseDto(group));
        return rolesList;
    }

    /*----------------- Groups from User Name -------------------*/
    @Override
    public Set<GroupResponseDto> getGroupsByUserId(Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos) {
        Set<GroupResponseDto> groupResponseDtoSet = new HashSet<>();

        userGroupMappingResponseDtos.forEach(userGroupMappingResponseDto -> {
            groupResponseDtoSet.add(this.getGroupById(userGroupMappingResponseDto.getGroupId()));
        });

        return groupResponseDtoSet;
    }
}
