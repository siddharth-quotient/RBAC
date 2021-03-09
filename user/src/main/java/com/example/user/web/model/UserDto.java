package com.example.user.web.model;

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
@Getter
public class UserDto {
    @Null(message = "User ID is auto generated - should be null")
    private Long userId;

    @Null(message = "Creation date is auto generated - should be null")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified date is auto generated - should be null")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "User Name cannot be Null")
    private String userName;

    @NotBlank(message = "First Name cannot be Null")
    private String firstName;

    @NotBlank(message = "Last Name cannot be Null")
    private String lastName;
}
