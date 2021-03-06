package com.example.role.repository;


import com.example.role.domain.GroupRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupRoleRepository extends JpaRepository<GroupRoleMapping, Long> {
    Set<GroupRoleMapping> findByGroupId(Long groupId);
    void deleteByRoleId(Long groupId);
}
