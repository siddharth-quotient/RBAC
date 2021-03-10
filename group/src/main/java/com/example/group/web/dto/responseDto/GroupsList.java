package com.example.group.web.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
/*----------------- (Wrapper) Response for Groups from User Name -------------------*/
public class GroupsList {
    private UserResponseDto user;
    private Set<GroupResponseDto> groups;
}
