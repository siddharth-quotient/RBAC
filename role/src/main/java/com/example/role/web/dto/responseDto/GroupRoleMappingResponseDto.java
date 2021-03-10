package com.example.role.web.dto.responseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/*----------------- Request for - Roles from Group ID -------------------*/
public class GroupRoleMappingResponseDto {

    private Long groupRoleId;
    private Long groupId;
    private Long roleId;
}
