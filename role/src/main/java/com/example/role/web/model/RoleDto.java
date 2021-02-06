package com.example.role.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
RoleDto {

    @Null(message = "Role ID should be null")
    private Long roleId;

    @Null
    private OffsetDateTime createDate;

    @Null
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Role Name cannot be blank")
    private String roleName;

    @NotBlank(message = "Role Description Cannot be blank")
    private String roleDescription;
}
