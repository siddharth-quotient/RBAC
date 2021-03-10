package com.example.group.web.dto.responseDto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllGroupsResponseDto {
    private Set<GroupResponseDto> groups;

}
