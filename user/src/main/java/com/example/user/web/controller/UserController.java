package com.example.user.web.controller;

import com.example.user.service.UserService;
import com.example.user.web.model.GroupsList;
import com.example.user.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Set<UserDto>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
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

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteByName(@PathVariable String userName){
        userService.deleteByName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*----------------- Groups from User Name -------------------*/
    @GetMapping("/{userName}/groups")
    public ResponseEntity<GroupsList> getGroupsByUserName(@PathVariable String userName){
        return new ResponseEntity<>(userService.getGroupsByUserName(userName), HttpStatus.OK);
    }
}
