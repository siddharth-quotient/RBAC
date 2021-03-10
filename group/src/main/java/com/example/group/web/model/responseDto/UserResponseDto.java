package com.example.group.web.model.responseDto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

/**
 *Data transfer object for users.
 *
 * @author Siddharth Mehta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- (Wrapped) Response for - Groups from User Name -------------------*/
public class UserResponseDto {
        private Long userId;
        private OffsetDateTime createDate;
        private OffsetDateTime lastModifiedDate;
        private String userName;
        private String firstName;
        private String lastName;
}
