package com.example.group.web.controller;

import com.example.group.service.GroupService;
import com.example.group.service.UserGroupService;
import com.example.group.web.model.requestDto.GroupRequestDto;
import com.example.group.web.model.requestDto.GroupUpdateRequestDto;
import com.example.group.web.model.responseDto.GroupResponseDto;
import com.example.group.web.model.responseDto.GroupsList;
import com.example.group.web.model.responseDto.RolesList;
import com.example.group.web.model.responseDto.UserGroupMappingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

/**
 * Exposes all Group - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;
    private final UserGroupService userGroupService;

    @GetMapping
    public ResponseEntity<Set<GroupResponseDto>> getAllGroups(){
        return new ResponseEntity<>(groupService.getAllGroups(), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.getGroupById(groupId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<GroupResponseDto> updateGroupById(@Valid @RequestBody GroupUpdateRequestDto groupUpdateRequestDto){
        return new ResponseEntity<>(groupService.updateGroupById(groupUpdateRequestDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(@Valid @RequestBody GroupRequestDto groupRequestDto){
        return new ResponseEntity<>(groupService.createGroup(groupRequestDto), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto> deleteById(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.deleteById(groupId), HttpStatus.OK);
    }


    /*----------------- Roles from Group Id -------------------*/
    @GetMapping("/{groupId}/roles")
    public ResponseEntity<RolesList> getRolesByGroupId(@PathVariable Long groupId){
        return new ResponseEntity<>(groupService.getRolesByGroupId(groupId), HttpStatus.OK);
    }

    /*----------------- Groups from User Name -------------------*/
    @PostMapping("/user-groups")
    public ResponseEntity<GroupsList> getGroupsByUserId(@RequestBody Set<UserGroupMappingResponseDto> userGroupMappingResponseDtos){
        GroupsList groupsList =new GroupsList();
        groupsList.setGroups(groupService.getGroupsByUserId(userGroupMappingResponseDtos));
        return new ResponseEntity<>(groupsList, HttpStatus.OK);
    }

    /*-------------- Check if a User has a Role ---------------*/
    @GetMapping("/userId/{userId}/roleId/{roleId}/check")
    public Boolean checkRoleIdForUserId(@PathVariable Long userId, @PathVariable Long roleId){
        Set<Long> groupIds = userGroupService.getGroupIdsForUserId(userId);
        return userGroupService.getRoleIdsForGroupIds(groupIds, roleId);
    }

}
