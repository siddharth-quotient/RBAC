package com.example.user.web.controller;

import com.example.user.service.UserGroupService;
import com.example.user.web.model.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.model.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.model.responseDto.UserGroupMappingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * Exposes all UserGroup - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-groups")
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity<Set<UserGroupMappingResponseDto>> getAllUserGroups(){
        return new ResponseEntity<>(userGroupService.getAllUserGroupMappings(), HttpStatus.OK);
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroupMappingResponseDto> getUserGroupMappingById(@PathVariable Long userGroupId){
        return new ResponseEntity<>(userGroupService.getUserGroupMappingById(userGroupId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserGroupMappingResponseDto> updateUserGroupMappingById(@Valid @RequestBody UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDto){
        return new ResponseEntity<>(userGroupService.updateUserGroupMappingById(userGroupMappingUpdateRequestDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<UserGroupMappingResponseDto> createUserGroupMapping(@Valid @RequestBody UserGroupMappingRequestDto userGroupMappingRequestDto){
        return new ResponseEntity<>(userGroupService.createUserGroupMapping(userGroupMappingRequestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userGroupId}")
    public ResponseEntity<UserGroupMappingResponseDto> deleteById(@PathVariable Long userGroupId){
        return new ResponseEntity<>(userGroupService.deleteById(userGroupId), HttpStatus.OK);
    }
}
