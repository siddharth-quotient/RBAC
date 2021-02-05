package com.example.group.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class GroupRoleMappingDto {

    @Null
    private Long groupRoleId;

    @NotNull
    private Long groupId;

    @NotNull
    private Long roleId;

    public Long getGroupRoleId() {
        return groupRoleId;
    }

    public void setGroupRoleId(Long groupRoleId) {
        this.groupRoleId = groupRoleId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
