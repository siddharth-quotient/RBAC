package com.example.role.service;

import com.example.role.domain.Role;
import com.example.role.repository.GroupRoleRepository;
import com.example.role.repository.RoleRepository;
import com.example.role.web.exception.RoleNameNotUniqueException;
import com.example.role.web.exception.RoleNotFoundException;
import com.example.role.web.mapper.RoleMapper;
import com.example.role.web.dto.requestDto.RoleRequestDto;
import com.example.role.web.dto.requestDto.RoleUpdateRequestDto;
import com.example.role.web.dto.responseDto.AllRolesResponseDto;
import com.example.role.web.dto.responseDto.GroupRoleMappingResponseDto;
import com.example.role.web.dto.responseDto.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation for Role Service
 *
 * @author Siddharth Mehta
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final GroupRoleRepository groupRoleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDto getRoleById(Long roleId) {
        if(roleId==null){
            throw new RoleNotFoundException("Role cannot be null");
        }
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (roleOpt.isPresent()){
            return roleMapper.roleToRoleResponseDto(roleOpt.get());
        }
        throw new RoleNotFoundException("Invalid Role Id :"+ roleId);
    }

    @Override
    public AllRolesResponseDto getAllRoles(){
        AllRolesResponseDto allRolesResponseDto = new AllRolesResponseDto();
        Set<RoleResponseDto> roles = new HashSet<>();

        roleRepository.findAll().forEach(role -> {
            roles.add(roleMapper.roleToRoleResponseDto(role));
        });

        allRolesResponseDto.setRoles(roles);
        return allRolesResponseDto;
    }

    @Override
    @Transactional
    public RoleResponseDto updateRoleById(RoleUpdateRequestDto roleUpdateRequestDto) {
        Long roleId = roleUpdateRequestDto.getRoleId();
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if(roleOptional.isPresent()){
            Role role = roleOptional.get();

            /*Check if role with given roleName already exists*/
            String dtoRoleName = roleUpdateRequestDto.getRoleName();
            Optional<Role> dtoRoleOptional = roleRepository.findByRoleName(dtoRoleName);

            if(dtoRoleOptional.isPresent()){
                Role dtoRole = dtoRoleOptional.get();
                if(dtoRole.getRoleId()!=role.getRoleId()){
                    throw new RoleNameNotUniqueException("Role by the name " + roleUpdateRequestDto.getRoleName() +" already exists!");
                }
            }

            role.setRoleName( roleUpdateRequestDto.getRoleName() );
            role.setRoleDescription( roleUpdateRequestDto.getRoleDescription() );

            return roleMapper.roleToRoleResponseDto(roleRepository.save(role));
        }
        log.error("Invalid Role Id provided while using updateRoleById: "+ roleId);
        throw new RoleNotFoundException("Invalid Role Id :"+ roleId);
    }

    @Override
    @Transactional
    public RoleResponseDto createRole(RoleRequestDto roleRequestDto) {

        try {
            return roleMapper.roleToRoleResponseDto(roleRepository.save(roleMapper.roleRequestDtoToRole(roleRequestDto)));
        }
        catch (DataIntegrityViolationException ex){
            log.error("Role by the name " + roleRequestDto.getRoleName() +" already exists!");
            throw new RoleNameNotUniqueException("RoleName " + roleRequestDto.getRoleName() +" already exists!");
        }
    }


    @Override
    @Transactional
    public RoleResponseDto deleteById(Long roleId) {
        if(roleId==null){
            throw new RoleNotFoundException("Role cannot be null");
        }

        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if(!roleOptional.isPresent()){
            throw new RoleNotFoundException("Invalid Role Id :"+ roleId);
        }

        roleRepository.deleteById(roleId);

        /*Delete all Group-Role Mappings for the deleted roleId */
        groupRoleRepository.deleteByRoleId(roleId);

        return roleMapper.roleToRoleResponseDto(roleOptional.get());
    }

    /*----------------- Roles from Group Id -------------------*/
    @Override
    public Set<RoleResponseDto> getRolesByGroupId(Set<GroupRoleMappingResponseDto> groupRoleMappingResponseDtos) {
        Set<RoleResponseDto> roleResponseDtos = new HashSet<>();

        groupRoleMappingResponseDtos.forEach(groupRoleMappingResponseDto -> {
            roleResponseDtos.add(this.getRoleById(groupRoleMappingResponseDto.getRoleId()));
        });

        return roleResponseDtos;
    }

}
