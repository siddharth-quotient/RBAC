package com.example.user.web.dto.responseDto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGroupMappingResponseDto {
    private Long userGroupId;
    private Long userId;
    private Long groupId;
}
