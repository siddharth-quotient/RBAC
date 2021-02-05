package com.example.group.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    @Null(message = "Group ID should be null")
    private Long groupId;

    @NotBlank(message = "Group Name cannot be blank")
    private String groupName;

    @NotBlank(message = "Group Description Cannot be blank")
    private String groupDescription;
}
