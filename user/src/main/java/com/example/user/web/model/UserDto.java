package com.example.user.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserDto {
    @Null(message = "User ID is self generated - should be null")
    private Long userId;

    @Null(message = "Creation date is self generated - should be null")
    private OffsetDateTime createDate;

    @Null(message = "Last Modified date is self generated - should be null")
    private OffsetDateTime lastModifiedDate;

    @NotBlank(message = "User Name cannot be blank")
    private String userName;

    @NotBlank(message = "First Name Cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name Cannot be blank")
    private String lastName;
}
