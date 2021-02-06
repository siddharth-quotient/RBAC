package com.example.group.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDto {

    private Long roleId;

    private String roleName;

    private String roleDescription;

}
