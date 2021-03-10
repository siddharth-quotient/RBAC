package com.example.user.web.mapper;

import com.example.user.domain.User;
import com.example.user.web.model.requestDto.UserRequestDto;
import com.example.user.web.model.responseDto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface UserMapper {
    //Flow:  UserRequestDto   ->  User Entity   ->   UserResponseDto

    User userRequestDtoToUser(UserRequestDto userRequestDto);

    UserResponseDto userToUserResponseDto(User user);

}
