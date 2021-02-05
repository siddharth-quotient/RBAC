package com.example.group.web.controller;

import com.example.group.domain.GroupRoleMapping;
import com.example.group.service.GroupRoleService;
import com.example.group.web.model.GroupRoleMappingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group-roles")
public class GroupRoleController {

    private final GroupRoleService groupRoleService;

    @PostMapping
    @Transactional
    public ResponseEntity<GroupRoleMappingDto> createGroupRole(@Valid @RequestBody GroupRoleMappingDto groupRoleMappingDto){
        return new ResponseEntity<>(groupRoleService.createGroupRole(groupRoleMappingDto), HttpStatus.CREATED);
    }
}
