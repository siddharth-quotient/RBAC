package com.example.role.web.dto.responseDto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllRolesResponseDto {
    private Set<RoleResponseDto> roles;

}
