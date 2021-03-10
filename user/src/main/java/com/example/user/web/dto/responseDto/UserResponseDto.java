package com.example.user.web.dto.responseDto;

import lombok.*;

import java.time.OffsetDateTime;

/**
 *Data transfer object representing a group.
 *
 * @author Siddharth Mehta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long userId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String userName;
    private String firstName;
    private String lastName;
}
