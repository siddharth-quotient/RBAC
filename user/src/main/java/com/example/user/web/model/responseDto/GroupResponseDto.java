package com.example.user.web.model.responseDto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*----------------- (Wrapped)Response from - Groups from User Name -------------------*/
public class GroupResponseDto {
    private Long groupId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String groupName;
    private String groupDescription;
}
