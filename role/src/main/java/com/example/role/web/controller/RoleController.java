package com.example.role.web.controller;


import com.example.role.service.RoleService;
import com.example.role.web.model.requestDto.RoleRequestDto;
import com.example.role.web.model.requestDto.RoleUpdateRequestDto;
import com.example.role.web.model.responseDto.AllRolesResponseDto;
import com.example.role.web.model.responseDto.GroupRoleMappingResponseDto;
import com.example.role.web.model.responseDto.RoleResponseDto;
import com.example.role.web.model.responseDto.RolesList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * Exposes all Role - RESTful web services
 *
 * @author Siddharth Mehta
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<AllRolesResponseDto> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long roleId){
        return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RoleResponseDto> updateRoleById(@Valid @RequestBody RoleUpdateRequestDto roleUpdateRequestDto){
        return new ResponseEntity<>(roleService.updateRoleById(roleUpdateRequestDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto roleRequestDto){
        return new ResponseEntity<>(roleService.createRole(roleRequestDto), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleResponseDto> deleteById(@PathVariable Long roleId){
        return new ResponseEntity<>(roleService.deleteById(roleId), HttpStatus.OK);
    }

    /*----------------- Roles from Group Id -------------------*/
    @PostMapping("/group-roles")
    public ResponseEntity<RolesList> getRolesByGroupId(@RequestBody Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos){
        /*Set<RoleResponseDto> roleResponseDtoSet = new HashSet<>();

        groupRoleMappingResponseDtos.forEach(groupRoleMappingResponseDto -> {
            roleResponseDtoSet.add( roleService.getRoleById(groupRoleMappingResponseDto.getRoleId()));
        });

        RolesList rolesList =new RolesList();
        rolesList.setRoles(roleResponseDtoSet);
        return new ResponseEntity<>(rolesList, HttpStatus.OK);*/


        RolesList rolesList =new RolesList();
        rolesList.setRoles(roleService.getRolesByGroupId(groupRoleMappingResponseDtos));
        return new ResponseEntity<>(rolesList, HttpStatus.OK);
    }
}
