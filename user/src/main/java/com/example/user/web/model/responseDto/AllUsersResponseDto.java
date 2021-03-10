package com.example.user.web.model.responseDto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllUsersResponseDto {
    private Set<UserResponseDto> users;

}
