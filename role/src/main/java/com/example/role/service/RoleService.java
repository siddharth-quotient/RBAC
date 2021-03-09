package com.example.role.service;

import com.example.role.web.model.RoleDto;

import java.util.Set;

/**
 * Interface that provides contract for Group Service
 *
 * @author Siddharth Mehta
 */
public interface RoleService {
    Set<RoleDto> getAllRoles();
    RoleDto getRoleById(Long roleId);
    RoleDto updateRoleById(Long roleId, RoleDto roleDto);
    RoleDto createRole(RoleDto roleDto);
    void deleteById(Long roleId);


}
