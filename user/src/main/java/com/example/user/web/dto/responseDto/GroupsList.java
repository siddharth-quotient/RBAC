package com.example.user.web.dto.responseDto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- (Wrapper)Response from - Groups from User Name -------------------*/
public class GroupsList {
    private UserResponseDto user;
    private Set<GroupResponseDto> groups;
}
