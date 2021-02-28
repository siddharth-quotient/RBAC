package com.example.user.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupDto {
    private Long groupId;
    private OffsetDateTime createDate;
    private OffsetDateTime lastModifiedDate;
    private String groupName;
    private String groupDescription;
}
