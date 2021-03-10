package com.example.role.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRoleMappingRequestDto {

    @Null(message = "Group-Role ID is auto-generated - should be null")
    private Long groupRoleId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

    @NotNull(message = "Role ID cannot be Null")
    private Long roleId;

}
