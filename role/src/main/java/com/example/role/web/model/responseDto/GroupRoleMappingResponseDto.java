package com.example.role.web.model.responseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
/*----------------- Request for - Roles from Group ID -------------------*/
public class GroupRoleMappingResponseDto {

    private Long groupRoleId;
    private Long groupId;
    private Long roleId;
}
