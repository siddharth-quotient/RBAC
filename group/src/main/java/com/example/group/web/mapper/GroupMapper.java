package com.example.group.web.mapper;

import com.example.group.domain.Group;
import com.example.group.web.model.requestDto.GroupRequestDto;
import com.example.group.web.model.responseDto.GroupResponseDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface GroupMapper {
    Group groupRequestDtoToGroup(GroupRequestDto groupRequestDto);
    GroupResponseDto groupToGroupResponseDto(Group group);
}
