package com.example.group.web.model;

import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *Data transfer object representing a group-role-mapping.
 *
 * @author Siddharth Mehta
 */
@ToString
public class GroupRoleMappingDto {

    @Null(message = "Group-Role ID is self generated - should be null")
    private Long groupRoleId;

    @NotNull(message = "Group ID should be specified")
    private Long groupId;

    @NotNull(message = "Role ID should be specified")
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
