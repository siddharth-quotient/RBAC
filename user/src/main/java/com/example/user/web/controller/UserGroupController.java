package com.example.user.web.controller;

import com.example.user.service.UserGroupService;
import com.example.user.web.model.ResponseDto;
import com.example.user.web.model.requestDto.UserGroupMappingRequestDto;
import com.example.user.web.model.requestDto.UserGroupMappingUpdateRequestDto;
import com.example.user.web.model.responseDto.UserGroupMappingResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public ResponseEntity<ResponseDto> getAllUserGroups(){
        return new ResponseEntity<>(new ResponseDto(userGroupService.getAllUserGroupMappings(), null), HttpStatus.OK);
    }

    @GetMapping("/{userGroupStringId}")
    public ResponseEntity<ResponseDto> getUserGroupMappingById(@PathVariable String userGroupStringId){
        return new ResponseEntity<>(new ResponseDto(userGroupService.getUserGroupMappingById(userGroupStringId), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateUserGroupMappingById(@Valid @RequestBody UserGroupMappingUpdateRequestDto userGroupMappingUpdateRequestDto){
        return new ResponseEntity<>(new ResponseDto(userGroupService.updateUserGroupMappingById(userGroupMappingUpdateRequestDto),null), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createUserGroupMapping(@Valid @RequestBody UserGroupMappingRequestDto userGroupMappingRequestDto){
        return new ResponseEntity<>(new ResponseDto(userGroupService.createUserGroupMapping(userGroupMappingRequestDto), null), HttpStatus.CREATED);
    }

    @DeleteMapping("/{userGroupStringId}")
    public ResponseEntity<ResponseDto> deleteById(@PathVariable String userGroupStringId){
        return new ResponseEntity<>(new ResponseDto(userGroupService.deleteById(userGroupStringId), null), HttpStatus.OK);
    }
}
