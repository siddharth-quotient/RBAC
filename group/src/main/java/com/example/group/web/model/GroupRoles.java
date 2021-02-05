package com.example.group.web.model;

import java.util.HashSet;
import java.util.Set;

public class GroupRoles {

    private Long groupId;
    private String groupName;
    private String groupDescription;

    private Set<RoleDto> roleDtoList = new HashSet<>();

    public GroupRoles() { }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Set<RoleDto> getRoleDtoList() {
        return roleDtoList;
    }

    public void setRoleDtoList(Set<RoleDto> roleDtoList) {
        this.roleDtoList = roleDtoList;
    }
}
