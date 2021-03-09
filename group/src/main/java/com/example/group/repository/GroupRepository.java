package com.example.group.repository;

import com.example.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for Group domain objects
 *
 * @author Siddharth Mehta
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
