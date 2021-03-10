package com.example.role.web.model.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class
RoleUpdateRequestDto {
    @NotNull(message = "Role ID cannot be Null")
    private Long roleId;

    @NotBlank(message = "Role Name cannot be Null")
    private String roleName;

    @NotBlank(message = "Role Description cannot be Null")
    private String roleDescription;
}
