package com.example.user.web.controller;

import com.example.user.service.UserService;
import com.example.user.web.model.requestDto.UserRequestDto;
import com.example.user.web.model.responseDto.GroupsList;
import com.example.user.web.model.ResponseDto;
import com.example.user.web.model.responseDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

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
    public ResponseEntity<Set<UserResponseDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponseDto> getUserByName(@PathVariable String userName){
        return new ResponseEntity<>(userService.getUserByName(userName), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUserByName(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.updateUserByName(userRequestDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{userName}")
    public ResponseEntity<UserResponseDto> deleteByName(@PathVariable String userName){

        return new ResponseEntity<>(userService.deleteByName(userName), HttpStatus.OK);
    }

    /*----------------- Groups from User Name -------------------*/
    @GetMapping("/{userName}/groups")
    public ResponseEntity<GroupsList> getGroupsByUserName(@PathVariable String userName){
        return new ResponseEntity<>(userService.getGroupsByUserName(userName), HttpStatus.OK);
    }

    /*-------------- Check if a User belongs to Group ---------------*/
    @GetMapping("/{userName}/groups/{groupId}/check")
    public ResponseEntity<ResponseDto> checkGroupIdForUserName(@PathVariable String userName, @PathVariable Long groupId){
        Boolean isValid = userService.checkGroupIdForUserName(userName, groupId);


        /*if(isValid){
            return new ResponseEntity<ResponseDto>(new ResponseDto(userName, null), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseDto>(new ResponseDto(null, new ExceptionResponse()), HttpStatus.OK);
        }*/

        return isValid?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /*-------------- Check if a User has a Role ---------------*/
    @GetMapping("/{userName}/roles/{roleId}/check")
    public ResponseEntity<?> checkRoleIdForUserName(@PathVariable String userName, @PathVariable Long roleId){
        Boolean isValid = userService.checkRoleIdForUserName(userName, roleId);

        return isValid?new ResponseEntity<>(HttpStatus.OK):new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
