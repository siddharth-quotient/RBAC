package com.example.group.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RolesList {
    private GroupDto groupDto;
    private Set<RoleDto> rolesSet;
}
