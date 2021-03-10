package com.example.user.web.dto.requestDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupMappingRequestDto {

    @Null(message = "User-Group ID is auto-generated - should be null")
    private Long userGroupId;

    @NotNull(message = "User ID cannot be Null")
    private Long userId;

    @NotNull(message = "Group ID cannot be Null")
    private Long groupId;

}
