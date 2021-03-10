package com.example.user.web.controller;

import com.example.user.service.UserService;
import com.example.user.web.model.requestDto.UserRequestDto;
import com.example.user.web.model.responseDto.AllUsersResponseDto;
import com.example.user.web.model.responseDto.GroupsList;
import com.example.user.web.model.ResponseDto;
import com.example.user.web.model.responseDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * Exposes all User - RESTful web services
 *
 * @author Siddharth Mehta
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ResponseDto> getAllUsers(){
        return new ResponseEntity<>(new ResponseDto(userService.getAllUsers(), null), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<ResponseDto> getUserByName(@PathVariable String userName){
        return new ResponseEntity<>(new ResponseDto(userService.getUserByName(userName), null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateUserByName(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(new ResponseDto(userService.updateUserByName(userRequestDto), null), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(new ResponseDto(userService.createUser(userRequestDto), null), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{userName}")
    public ResponseEntity<ResponseDto> deleteByName(@PathVariable String userName){

        return new ResponseEntity<>(new ResponseDto(userService.deleteByName(userName), null), HttpStatus.OK);
    }

    /*----------------- Groups from User Name -------------------*/
    @GetMapping("/{userName}/groups")
    public ResponseEntity<ResponseDto> getGroupsByUserName(@PathVariable String userName){
        return new ResponseEntity<>(new ResponseDto(userService.getGroupsByUserName(userName), null), HttpStatus.OK);
    }

    /*-------------- Check if a User belongs to Group ---------------*/
    @GetMapping("/{userName}/groups/{groupId}/check")
    public ResponseEntity<?> checkGroupIdForUserName(@PathVariable String userName, @PathVariable Long groupId){
        Boolean isValid = userService.checkGroupIdForUserName(userName, groupId);

        return isValid?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /*-------------- Check if a User has a Role ---------------*/
    @GetMapping("/{userName}/roles/{roleId}/check")
    public ResponseEntity<?> checkRoleIdForUserName(@PathVariable String userName, @PathVariable Long roleId){
        Boolean isValid = userService.checkRoleIdForUserName(userName, roleId);

        return isValid?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
