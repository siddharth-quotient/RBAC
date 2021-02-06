package com.example.group.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDto {

    private Long roleId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String roleName;
    private String roleDescription;

}
