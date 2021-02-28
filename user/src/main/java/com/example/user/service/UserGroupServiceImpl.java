package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.domain.UserGroupMapping;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.exception.UserGroupNotFoundException;
import com.example.user.web.mapper.UserGroupMapper;
import com.example.user.web.model.UserGroupMappingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final UserGroupMapper userGroupMapper;

    @Override
    public Set<UserGroupMappingDto> getUserGroupMappings() {
        Set<UserGroupMappingDto> userGroups = new HashSet<>();

        userGroupRepository.findAll().forEach(userGroupMapping -> {
            userGroups.add(userGroupMapper.userGroupMappingToUserGroupDto(userGroupMapping));
        });
        return userGroups;
    }

    @Override
    public UserGroupMappingDto getUserGroupMappingById(Long userGroupId) {
        if(userGroupId==null){
            throw new UserGroupNotFoundException("User-Group Mapping cannot be Null");
        }

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository.findById(userGroupId);
        if(userGroupMappingOptional.isPresent()){
            return userGroupMapper.userGroupMappingToUserGroupDto(userGroupMappingOptional.get());
        }
        log.error("Invalid User-Group Mapping Id provided while using getUserGroupMappingById: "+ userGroupId);
        throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id: "+ userGroupId);
    }

    @Override
    public UserGroupMappingDto updateUserGroupMappingById(Long userGroupId, UserGroupMappingDto userGroupMappingDto) {
        if(userGroupId==null){
            throw new UserGroupNotFoundException("User-Group Mapping cannot be Null");
        }

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository.findById(userGroupId);

        if(userGroupMappingOptional.isPresent()){
            UserGroupMapping userGroupMapping = userGroupMappingOptional.get();

            Long userId = userGroupMappingDto.getUserId();

            /* Check for valid user_id */
            if(!validateUserId(userId)){
                throw new UserGroupNotFoundException("Invalid User Id: "+ userId);
            }

            //todo How to implement validation for group id?

            userGroupMapping.setUserId( userGroupMappingDto.getUserId() );
            userGroupMapping.setGroupId( userGroupMappingDto.getGroupId() );

            return userGroupMapper.userGroupMappingToUserGroupDto(userGroupRepository.save(userGroupMapping));
        }
        log.error("Invalid User-Group Mapping Id provided while using updateUserGroupMappingById: "+ userGroupId);
        throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id :"+ userGroupId);
    }

    @Override
    public UserGroupMappingDto createUserGroupMapping(UserGroupMappingDto userGroupMappingDto) {
        if(userGroupMappingDto == null){
            throw new UserGroupNotFoundException("User-Group Mapping cannot be Null");
        }

        Long userId = userGroupMappingDto.getUserId();

        /* Check for valid user_id */
        if(!validateUserId(userId)){
            throw new UserGroupNotFoundException("Invalid User Id: "+ userId);
        }

        //todo How to implement validation for group id?

        return userGroupMapper.userGroupMappingToUserGroupDto(userGroupRepository.save(userGroupMapper.userGroupMappingDtoToUserGroup(userGroupMappingDto)));
    }

    @Override
    public void deleteById(Long userGroupId) {
        if(userGroupId==null){
            throw new UserGroupNotFoundException("User-Group Mapping cannot be Null");
        }

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository.findById(userGroupId);

        if(!userGroupMappingOptional.isPresent()){
            log.error("Invalid User-Group Mapping Id provided while using deleteById: "+ userGroupId);
            throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id: "+ userGroupId);
        }

        userGroupRepository.deleteById(userGroupId);
    }

    public boolean validateUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return (userOptional.isPresent());
    }
}
