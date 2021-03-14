package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.repository.UserGroupRepository;
import com.example.user.repository.UserRepository;
import com.example.user.web.dto.responseDto.AllUsersResponseDto;
import com.example.user.web.dto.responseDto.UserResponseDto;
import com.example.user.web.mapper.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapperMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    UserServiceImpl userService;

    User userMock1;
    User userMock2;
    UserResponseDto userResponseDtoMock1;
    UserResponseDto userResponseDtoMock2;

    @BeforeEach
    public void setUp(){
        //Some Mock User Objects
        userMock1 = User.builder().userId(1L)
                .userName("smehta").firstName("Sid").lastName("Mehta")
                .build();
        userMock2 = User.builder().userId(2L)
                .userName("gyadav").firstName("Gaurav").lastName("Yadav")
                .build();


        //Couple of Mock UserResponseDto objects
         userResponseDtoMock1 = UserResponseDto.builder().userId(1L)
                .userName("smehta").firstName("Sid").lastName("Mehta")
                .build();
        userResponseDtoMock2 = UserResponseDto.builder().userId(2L)
                .userName("gyadav").firstName("Gaurav").lastName("Yadav")
                .build();
    }
    @Test
    void getAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(userMock1, userMock2));
        when(userMapperMock.userToUserResponseDto(userMock1)).thenReturn(userResponseDtoMock1);
        when(userMapperMock.userToUserResponseDto(userMock2)).thenReturn(userResponseDtoMock2);
        //test
        AllUsersResponseDto allUsersResponseDto = userService.getAllUsers();
        Set<UserResponseDto> userResponseDtoSet = allUsersResponseDto.getUsers();

        assertEquals(2, userResponseDtoSet.size());
        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    void getUserByName() {
        when(userRepositoryMock.findByUserName("smehta")).thenReturn(Optional.of(userMock1));
        when(userMapperMock.userToUserResponseDto(userMock1)).thenReturn(userResponseDtoMock1);

        UserResponseDto userByName = userService.getUserByName("smehta");
        assertEquals(userMock1.getUserName(), userByName.getUserName());
        verify(userRepositoryMock, times(1)).findByUserName(anyString());
    }
}