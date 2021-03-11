package com.example.role.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Request Data Transfer Object for updating GroupRoleMappings (groupRoleId not null).
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRoleMappingUpdateRequestDto {

    @NotNull(message = "Group-Role ID cannot be null")
    private Long groupRoleId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

    @NotNull(message = "Role ID cannot be Null")
    private Long roleId;

}