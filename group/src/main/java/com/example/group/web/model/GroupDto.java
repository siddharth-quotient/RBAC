package com.example.group.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    @Null(message = "Group ID should be null")
    private Long groupId;

    @Null
    private OffsetDateTime createDate;

    @Null
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Group Name cannot be blank")
    private String groupName;

    @NotBlank(message = "Group Description Cannot be blank")
    private String groupDescription;
}
