package com.example.role.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;

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