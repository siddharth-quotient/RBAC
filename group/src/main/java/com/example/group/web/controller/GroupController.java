package com.example.group.web.controller;

import com.example.group.service.GroupService;
import com.example.group.service.UserGroupService;
import com.example.group.web.dto.ResponseDto;
import com.example.group.web.dto.requestDto.GroupRequestDto;
import com.example.group.web.dto.requestDto.GroupUpdateRequestDto;
import com.example.group.web.dto.responseDto.*;
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
    public ResponseEntity<ResponseDto> getAllGroups(){
        return new ResponseEntity<>(new ResponseDto( groupService.getAllGroups(), null), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<ResponseDto> getGroupById(@PathVariable Long groupId){
        return new ResponseEntity<>(new ResponseDto(groupService.getGroupById(groupId), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateGroupById(@Valid @RequestBody GroupUpdateRequestDto groupUpdateRequestDto){
        return new ResponseEntity<>(new ResponseDto(groupService.updateGroupById(groupUpdateRequestDto), null), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createGroup(@Valid @RequestBody GroupRequestDto groupRequestDto){
        return new ResponseEntity<>(new ResponseDto(groupService.createGroup(groupRequestDto), null), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{groupId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable Long groupId){
        return new ResponseEntity<>(new ResponseDto(groupService.deleteById(groupId), null), HttpStatus.OK);
    }


    /*----------------- Roles from Group Id -------------------*/
    @GetMapping("/{groupId}/roles")
    public ResponseEntity<ResponseDto> getRolesByGroupId(@PathVariable Long groupId){
        return new ResponseEntity<>(new ResponseDto(groupService.getRolesByGroupId(groupId), null), HttpStatus.OK);
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
