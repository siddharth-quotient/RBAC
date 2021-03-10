package com.example.user.service;

import com.example.user.web.model.requestDto.UserRequestDto;
import com.example.user.web.model.responseDto.AllUsersResponseDto;
import com.example.user.web.model.responseDto.GroupsList;
import com.example.user.web.model.responseDto.UserResponseDto;

import java.util.Set;

/**
 * Interface that provides contract for User Service
 *
 * @author Siddharth Mehta
 */
public interface UserService {
    AllUsersResponseDto getAllUsers();
    UserResponseDto getUserByName(String userName);
    UserResponseDto updateUserByName(UserRequestDto userRequestDto);
    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto deleteByName(String userName);

    /*----------------- Groups from UserName -------------------*/
    GroupsList getGroupsByUserName(String userName);

    /*-------------- Check if a User belongs to Group ---------------*/
    Boolean checkGroupIdForUserName(String userName, Long groupId);

    /*-------------- Check if a User has a Role ---------------*/
    Boolean checkRoleIdForUserName(String userName, Long roleId);
}
