package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.domain.UserGroupMapping;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.exception.UserNameNotUniqueException;
import com.example.user.web.exception.UserNotFoundException;
import com.example.user.web.mapper.UserGroupMapper;
import com.example.user.web.mapper.UserMapper;
import com.example.user.web.model.GroupsList;
import com.example.user.web.model.UserDto;
import com.example.user.web.model.UserGroupMappingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for User Service
 *
 * @author Siddharth Mehta
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserGroupMapper userGroupMapper;
    private final UserMapper userMapper;
    private final GroupListByUserIdHystrix groupListByUserIdHystrix;
    private final ValidateRoleForUserId validateRoleForUserId;

    @Override
    public Set<UserDto> getAllUsers() {
        Set<UserDto> users = new HashSet<>();

        userRepository.findAll().forEach(user -> {
            users.add(userMapper.userToUserDto(user));
        });
        return users;
    }

    @Override
    public UserDto getUserByName(String userName) {
        User user = getUserByUserName(userName);
        return  userMapper.userToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUserByName(String userName, UserDto userDto) {

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(userOptional.isPresent()){
            User user = userOptional.get();

            /*Check if user with given username already exists*/
            String dtoUserName = userDto.getUserName();
            Optional<User> dtoUserOptional = userRepository.findByUserName(dtoUserName);

            if(dtoUserOptional.isPresent()){
                User dtoUser = dtoUserOptional.get();
                if(dtoUser.getUserId()!=user.getUserId()){
                    throw new UserNameNotUniqueException("User by the name " +userDto.getUserName() +" already exists!");
                }
            }

            user.setUserName(userDto.getUserName());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());

            return userMapper.userToUserDto(userRepository.save(user));

        }

        log.error("Invalid User Name provided while using getUserByName: "+ userName);
        throw new UserNotFoundException("Invalid User with name: "+ userName);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if(userDto ==  null){
            throw new UserNotFoundException("New User cannot be null");
        }
        try {
            return userMapper.userToUserDto(userRepository.save(userMapper.userDtoToUser(userDto)));
        }
        catch (DataIntegrityViolationException ex){
            throw new UserNameNotUniqueException("User by the name " +userDto.getUserName() +" already exists!");
        }

    }

    @Override
    @Transactional
    public void deleteByName(String userName) {
        if(userName==null || userName.isEmpty()){
            throw new UserNotFoundException("User cannot be null");
        }

        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(!userOptional.isPresent()) {
            log.error("Invalid User Name provided while using deleteByName: "+ userName);
            throw new UserNotFoundException("Invalid User Name: "+ userName);
        }

        Long userId = userOptional.get().getUserId();
        userRepository.deleteByUserName(userName);

        /*Delete all User-Group Mappings for the deleted userId */
        userGroupRepository.deleteByUserId(userId);
    }

    /*----------------- Groups from User Name -------------------*/
    @Override
    @Transactional
    public GroupsList getGroupsByUserName(String userName) {
        User user = getUserByUserName(userName);
        Long userId = user.getUserId();
        Set<UserGroupMappingDto> userGroupMappingDtos = new HashSet<>();

        userGroupRepository.findByUserId(userId).forEach(userGroupMapping -> {
            userGroupMappingDtos.add(userGroupMapper.userGroupMappingToUserGroupDto(userGroupMapping));
        });

        ResponseEntity<GroupsList>  groupsListResponseEntity = groupListByUserIdHystrix.getGroupListByUserId(userGroupMappingDtos);
        GroupsList groupsList = groupsListResponseEntity.getBody();
        groupsList.setUserDto(userMapper.userToUserDto(user));
        return groupsList;
    }

    /*-------------- Check if a User belongs to Group ---------------*/
    @Override
    public Boolean checkGroupIdForUserName(String userName, Long groupId) {
        User user = getUserByUserName(userName);
        Long userId = user.getUserId();

        Optional<UserGroupMapping> userGroupMappingOptional = userGroupRepository
                .findUserGroupMappingByUserIdAndGroupId(userId, groupId);

        return userGroupMappingOptional.isPresent();
    }

    /*-------------- Check if a User has a Role ---------------*/
    @Override
    public Boolean checkRoleIdForUserName(String userName, Long roleId) {
        User user = getUserByUserName(userName);
        Long userId = user.getUserId();

        return validateRoleForUserId.checkRolePermissionExistForUser(userId, roleId);
    }

    //Helper function
    User getUserByUserName(String userName){
        Optional<User> userOptional = userRepository.findByUserName(userName);

        if(!userOptional.isPresent()) {
            log.error("Invalid User Name: "+ userName);
            throw new UserNotFoundException("Invalid User Name: "+ userName);
        }

        return userOptional.get();
    }

}
