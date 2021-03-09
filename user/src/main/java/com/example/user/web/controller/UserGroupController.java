package com.example.user.web.controller;

import com.example.user.service.UserGroupService;
import com.example.user.web.model.UserGroupMappingDto;
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
    public ResponseEntity<Set<UserGroupMappingDto>> getAllUserGroups(){
        return new ResponseEntity<>(userGroupService.getAllUserGroupMappings(), HttpStatus.OK);
    }

    @GetMapping("/{userGroupId}")
    public ResponseEntity<UserGroupMappingDto> getUserGroupMappingById(@PathVariable Long userGroupId){
        return new ResponseEntity<>(userGroupService.getUserGroupMappingById(userGroupId), HttpStatus.OK);
    }

    @PutMapping("/{userGroupId}")
    public ResponseEntity<UserGroupMappingDto> updateUserGroupMappingById(@PathVariable Long userGroupId, @Valid @RequestBody UserGroupMappingDto userGroupMappingDto){
        return new ResponseEntity<>(userGroupService.updateUserGroupMappingById(userGroupId ,userGroupMappingDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<UserGroupMappingDto> createUserGroupMapping(@Valid @RequestBody UserGroupMappingDto userGroupMappingDto){
        return new ResponseEntity<>(userGroupService.createUserGroupMapping(userGroupMappingDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userGroupId}")
    public ResponseEntity<UserGroupMappingDto> deleteById(@PathVariable Long userGroupId){
        return new ResponseEntity<>(userGroupService.deleteById(userGroupId), HttpStatus.OK);
    }
}
