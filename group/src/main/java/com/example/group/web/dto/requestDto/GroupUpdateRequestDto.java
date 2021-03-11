package com.example.group.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Request Data Transfer Object for updating Groups (groupId not null).
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupUpdateRequestDto {
    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

    @NotBlank(message = "Group Name should cannot be Null")
    private String groupName;

    @NotBlank(message = "Group Description cannot be Null")
    private String groupDescription;
}
