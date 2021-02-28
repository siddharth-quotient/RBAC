package com.example.user.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@ToString
@Getter
@Setter
public class UserGroupMappingDto {

    @Null(message = "User-Group ID is self generated - should be null")
    private Long userGroupId;

    @NotNull(message = "User ID should be specified")
    private Long userId;

    @NotNull(message = "Group ID should be specified")
    private Long groupId;

}
