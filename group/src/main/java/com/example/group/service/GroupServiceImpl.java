package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.domain.GroupRoleMapping;
import com.example.group.repository.GroupRepository;
import com.example.group.repository.GroupRoleRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.mapper.GroupMapper;
import com.example.group.web.mapper.GroupRoleMapper;
import com.example.group.web.model.GroupDto;
import com.example.group.web.model.GroupRoleMappingDto;
import com.example.group.web.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
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
            throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
        }

        groupRepository.deleteById(groupId);
    }

    /*----------------- Roles from Group Ids -------------------*/
    @Override
    public Set<RoleDto> getRolesByGroupId(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(!groupOptional.isPresent()){
            throw new GroupNotFoundException("Invalid Group Id :"+ groupId);
        }
        Set<GroupRoleMappingDto> groupRoles = new HashSet<>();

        groupRoleRepository.findByGroupId(groupId).forEach(groupRoleMapping -> {
            groupRoles.add(groupRoleMapper.groupRoleMappingToGroupRoleMappingDto(groupRoleMapping));
        });

        log.debug("Loading Roles for group id: "+groupId);
        for(GroupRoleMappingDto groupRoleMappingDto: groupRoles){
            log.debug(groupRoleMappingDto.toString());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(groupRoles,headers);
        ResponseEntity<Set<RoleDto>> responseEntity = restTemplate.exchange("http://localhost:8080/roles/group-roles/", HttpMethod.POST, requestEntity,new ParameterizedTypeReference<Set<RoleDto>>() {});

        return responseEntity.getBody();
    }
}
