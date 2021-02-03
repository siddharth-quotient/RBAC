package com.example.role.web.controller;

import com.example.role.service.RoleService;
import com.example.role.web.model.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<Set<RoleDto>> getRoles(){
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId){
        return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRoleById(@PathVariable Long roleId, @Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.updateRoleById(roleId ,roleDto), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(roleService.createRole(roleDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteById(@PathVariable Long roleId){
        roleService.deleteById(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
