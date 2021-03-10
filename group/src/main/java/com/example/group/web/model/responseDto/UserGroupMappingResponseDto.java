package com.example.group.web.model.responseDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *Data transfer object representing a user-group-mapping.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- Request for - Groups from User Name -------------------*/
public class UserGroupMappingResponseDto {
    private Long userGroupId;
    private Long userId;
    private Long groupId;
}
