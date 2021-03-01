package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.mapper.GroupMapper;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.model.GroupDto;
import com.example.group.web.model.GroupRoleMappingDto;
import com.example.group.web.model.RoleDto;
import com.example.group.web.model.RolesList;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final GroupMapper groupMapper;
    private final GroupRoleMapper groupRoleMapper;
    private final RestTemplate restTemplate;


    @Override
    public Set<GroupDto> getGroups() {
        Set<GroupDto> groups = new HashSet<>();

        groupRepository.findAll().forEach(group -> {
            groups.add(groupMapper.groupToGroupDto(group));
        });
        return groups;

    }

    @Override
    public GroupDto getGroupById(Long groupId) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isPresent()){
            return groupMapper.groupToGroupDto(groupOptional.get());
        }
        log.error("Invalid Group Id provided while using getGroupById: "+ groupId);
        throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
    }

    @Override
    @Transactional
    public GroupDto updateGroupById(Long groupId, GroupDto groupDto) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }


        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if(groupOptional.isPresent()){
            Group group = groupOptional.get();

            group.setGroupName( groupDto.getGroupName() );
            group.setGroupDescription( groupDto.getGroupDescription() );

            return groupMapper.groupToGroupDto(groupRepository.save(group));
        }
        log.error("Invalid Group Id provided while using updateGroupById: "+ groupId);
        throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
    }

    @Override
    @Transactional
    public GroupDto createGroup(GroupDto groupDto) {
        if(groupDto == null){
            throw new GroupNotFoundException("New Group cannot be Null");
        }

        return groupMapper.groupToGroupDto(groupRepository.save(groupMapper.groupDtoToGroup(groupDto)));
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) {
        if(groupId==null){
            throw new GroupNotFoundException("Group cannot be null");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using deleteById: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
        }

        groupRepository.deleteById(groupId);
    }

    /*----------------- Roles from Group Id -------------------*/

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "getFallbackRolesByGroupId")
    public RolesList getRolesByGroupId(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using getRolesByGroupId: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id: "+ groupId);
        }
        Group group = groupOptional.get();
        Set<GroupRoleMappingDto> groupRoles = new HashSet<>();

        groupRoleRepository.findByGroupId(groupId).forEach(groupRoleMapping -> {
            groupRoles.add(groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleMapping));
        });

        ResponseEntity<RolesList> rolesListResponseEntity = restTemplate.postForEntity("http://role-service/roles/group-roles/", groupRoles, RolesList.class);
        RolesList rolesList = rolesListResponseEntity.getBody();
        rolesList.setGroupDto(groupMapper.groupToGroupDto(group));
        return rolesList;
    }

    public RolesList getFallbackRolesByGroupId(Long groupId) {

        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            log.error("Invalid Group Id provided while using getRolesByGroupId: "+ groupId);
            throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
        }

        RolesList fallBackRoleList = new RolesList();
        Set<RoleDto> roleDtoHashSet = new HashSet<>();

        fallBackRoleList.setGroupDto(groupMapper.groupToGroupDto(groupOptional.get()));
        groupRoleRepository.findByGroupId(groupId).forEach(groupRoleMapping -> {
            roleDtoHashSet.add(new RoleDto(groupRoleMapping.getRoleId(), null, null,
                    "Role Name unavailable", "Role Description Unavailable"));
        });
        fallBackRoleList.setRolesSet(roleDtoHashSet);
        return fallBackRoleList;
    }
}
