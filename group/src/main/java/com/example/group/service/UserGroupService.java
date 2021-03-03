package com.example.group.service;


import com.example.group.web.model.UserGroupMappingDto;

import java.util.Set;

public interface UserGroupService {
    Set<Long> getGroupIdsForUserId(Long userId);
    Boolean getRoleIdsForGroupIds(Set<Long> groupIds, Long roleId);
}
