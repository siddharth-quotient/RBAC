package com.example.role.web.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*----------------- (Wrapped) Response for - Roles from Group ID -------------------*/
public class GroupResponseDto {

    private Long groupId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String groupName;
    private String groupDescription;
}