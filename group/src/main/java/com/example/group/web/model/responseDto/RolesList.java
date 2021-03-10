package com.example.group.web.model.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Wrapper Object to  provide a data transfer object of role list along with groups.
 *
 * @author Siddharth Mehta
 */
@Getter
@Setter
/*----------------- (Wrapper)Response from - Roles from Group ID -------------------*/
public class RolesList {
    private GroupResponseDto group;
    private Set<RoleResponseDto> roles;
}
