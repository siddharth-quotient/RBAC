package com.example.user.web.controller;

import com.example.user.service.UserService;
import com.example.user.web.model.GroupsList;
import com.example.user.web.model.ResponseDto;
import com.example.user.web.model.UserDto;
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
    public ResponseEntity<Set<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String userName){
        return new ResponseEntity<>(userService.getUserByName(userName), HttpStatus.OK);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserDto> updateUserByName(@PathVariable String userName, @Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUserByName(userName ,userDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{userName}")
    public ResponseEntity<UserDto> deleteByName(@PathVariable String userName){

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
