package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.domain.UserGroupMapping;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.exception.UserGroupNotFoundException;
import com.example.user.web.exception.UserGroupNotUniqueException;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.UserGroupMapper;
import com.example.user.web.model.UserGroupMappingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for UserGroup Service
 *
 * @author Siddharth Mehta
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final UserGroupMapper userGroupMapper;
    private final ValidateGroupForUserGroupMapping validateGroupForUserGroupMapping;

    @Override
    public Set<UserGroupMappingDto> getAllUserGroupMappings() {
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
    @Transactional
    public UserGroupMappingDto updateUserGroupMappingById(Long userGroupId, UserGroupMappingDto userGroupMappingDto) {
        if(userGroupId==null){
            throw new UserGroupNotFoundException("User-Group Mapping cannot be Null");
        }

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository.findById(userGroupId);

        if(!userGroupMappingOptional.isPresent()){
            log.error("Invalid User-Group Mapping Id provided while using updateUserGroupMappingById: "+ userGroupId);
            throw new UserGroupNotFoundException("Invalid User-Group Mapping with Id :"+ userGroupId);
        }else{
            UserGroupMapping userGroupMapping = userGroupMappingOptional.get();

            Long userId = userGroupMappingDto.getUserId();
            Long groupId = userGroupMappingDto.getGroupId();

            /* Check if the same userId and groupId combination already exists*/
            Optional<UserGroupMapping> dtoUserGroupMappingOptional = userGroupRepository
                    .findUserGroupMappingByUserIdAndGroupId(userGroupMappingDto.getUserId(), userGroupMappingDto.getGroupId());

            if(dtoUserGroupMappingOptional.isPresent()){
                UserGroupMapping dtoUserGroupMapping = dtoUserGroupMappingOptional.get();
                if(userGroupMapping.getUserGroupId()!=dtoUserGroupMapping.getUserGroupId()){
                    throw new UserGroupNotUniqueException("UserId: "+userId+" and GroupId: "+groupId+" lookup value already exist");
                }
            }

            /* Check for valid userId */
            if(!validateUserId(userId)){
                throw new UserNotFoundException("Invalid User Id: "+ userId);
            }

            /* Check for valid groupId */
            validateGroupForUserGroupMapping.checkGroupExist(groupId);

            userGroupMapping.setUserId( userGroupMappingDto.getUserId() );
            userGroupMapping.setGroupId( userGroupMappingDto.getGroupId() );

            return userGroupMapper.userGroupMappingToUserGroupDto(userGroupRepository.save(userGroupMapping));
        }

    }

    @Override
    @Transactional
    public UserGroupMappingDto createUserGroupMapping(UserGroupMappingDto userGroupMappingDto) {
        if (userGroupMappingDto == null) {
            throw new UserGroupNotFoundException("User-Group Mapping cannot be Null");
        }

        Long userId = userGroupMappingDto.getUserId();
        Long groupId = userGroupMappingDto.getGroupId();

        /* Check for valid userId */
        if (!validateUserId(userId)) {
            throw new UserNotFoundException("Invalid User Id: " + userId);
        }

        /* Check for valid groupId */
        validateGroupForUserGroupMapping.checkGroupExist(groupId);

        try {
            return userGroupMapper.userGroupMappingToUserGroupDto(userGroupRepository.save(userGroupMapper.userGroupMappingDtoToUserGroup(userGroupMappingDto)));
        }
        catch (DataIntegrityViolationException ex){
            throw new UserGroupNotUniqueException("UserId: "+userId+" and GroupId: "+groupId+" lookup value already exist");
        }
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
