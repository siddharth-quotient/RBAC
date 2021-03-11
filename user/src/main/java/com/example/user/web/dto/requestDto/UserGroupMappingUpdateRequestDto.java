package com.example.user.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Request Data Transfer Object for updating UserGroupMappings (userGroupId not null).
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupMappingUpdateRequestDto {

    @NotNull(message = "User-Group ID cannot be null")
    private Long userGroupId;

    @NotNull(message = "User ID cannot be Null")
    private Long userId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

}
