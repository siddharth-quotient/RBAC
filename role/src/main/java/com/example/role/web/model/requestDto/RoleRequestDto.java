package com.example.role.web.model.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class
RoleRequestDto {

    @Null(message = "Role ID is auto generated - should be Null")
    private Long roleId;

    @NotBlank(message = "Role Name cannot be Null")
    private String roleName;

    @NotBlank(message = "Role Description cannot be Null")
    private String roleDescription;
}
