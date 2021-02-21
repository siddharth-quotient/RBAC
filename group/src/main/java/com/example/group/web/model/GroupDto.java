package com.example.group.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

/**
 *Data transfer object representing a group.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    @Null(message = "Group ID is self generated - should be null")
    private Long groupId;

    @Null(message = "Creation date is self generated - should be null")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified date is self generated - should be null")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "Group Name cannot be blank")
    private String groupName;

    @NotBlank(message = "Group Description Cannot be blank")
    private String groupDescription;
}
