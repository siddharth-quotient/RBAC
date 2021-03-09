package com.example.user.service;

import com.example.user.web.model.GroupsList;
import com.example.user.web.model.UserDto;

import java.util.Set;

/**
 * Interface that provides contract for User Service
 *
 * @author Siddharth Mehta
 */
public interface UserService {
    Set<UserDto> getAllUsers();
    UserDto getUserByName(String userName);
    UserDto updateUserByName(String userName, UserDto userDto);
    UserDto createUser(UserDto userDto);
    void deleteByName(String userName);

    /*----------------- Groups from UserName -------------------*/
    GroupsList getGroupsByUserName(String userName);

    /*-------------- Check if a User belongs to Group ---------------*/
    Boolean checkGroupIdForUserName(String userName, Long groupId);

    /*-------------- Check if a User has a Role ---------------*/
    Boolean checkRoleIdForUserName(String userName, Long roleId);
}
