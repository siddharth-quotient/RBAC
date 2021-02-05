package com.example.group.service;

import com.example.group.domain.Group;
import com.example.group.repository.GroupRepository;
import com.example.group.web.exception.GroupNotFoundException;
import com.example.group.web.mapper.GroupMapper;
import com.example.group.web.model.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
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
}
