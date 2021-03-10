package com.example.group.web.controller;

import com.example.group.service.GroupRoleService;
import com.example.group.web.model.requestDto.GroupRoleMappingRequestDto;
import com.example.group.web.model.requestDto.GroupRoleMappingUpdateRequestDto;
import com.example.group.web.model.responseDto.GroupRoleMappingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * Exposes all GroupRole - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-roles")
public class GroupRoleController {

    private final GroupRoleService groupRoleService;

    @GetMapping
    public ResponseEntity<Set<GroupRoleMappingResponseDto>> getAllGroupRoles(){
        return new ResponseEntity<>(groupRoleService.getAllGroupRoleMappings(), HttpStatus.OK);
    }

    @GetMapping("/{groupRoleId}")
    public ResponseEntity<GroupRoleMappingResponseDto> getGroupRoleMappingById(@PathVariable Long groupRoleId){
        return new ResponseEntity<>(groupRoleService.getGroupRoleMappingById(groupRoleId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GroupRoleMappingResponseDto> updateGroupRoleMappingById(@Valid @RequestBody GroupRoleMappingUpdateRequestDto groupRoleMappingUpdateRequestDto){
        return new ResponseEntity<>(groupRoleService.updateGroupRoleMappingById(groupRoleMappingUpdateRequestDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<GroupRoleMappingResponseDto> createGroupRoleMapping(@Valid @RequestBody GroupRoleMappingRequestDto groupRoleMappingRequestDto){
        return new ResponseEntity<>(groupRoleService.createGroupRoleMapping(groupRoleMappingRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupRoleId}")
    public ResponseEntity<GroupRoleMappingResponseDto> deleteById(@PathVariable Long groupRoleId){
        return new ResponseEntity<>(groupRoleService.deleteById(groupRoleId), HttpStatus.OK);
    }

}
