package com.example.group.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * Request Data Transfer Object for Group.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRequestDto {
    @Null(message = "Group ID is auto-generated - should be Null")
    private Long groupId;

    @NotBlank(message = "Group Name should cannot be Null")
    private String groupName;

    @NotBlank(message = "Group Description cannot be Null")
    private String groupDescription;
}
