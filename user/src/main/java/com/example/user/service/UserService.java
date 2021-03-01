package com.example.user.service;

import com.example.user.web.model.GroupsList;
import com.example.user.web.model.UserDto;

import java.util.Set;

public interface UserService {
    Set<UserDto> getUsers();
    UserDto getUserByName(String userName);
    UserDto updateUserByName(String userName, UserDto userDto);
    UserDto createUser(UserDto userDto);
    void deleteByName(String userName);

    /*----------------- Groups from UserName -------------------*/
    GroupsList getGroupsByUserName(String userName);
}
