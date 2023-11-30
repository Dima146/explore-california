package com.company.explorecalifornia.repository;

import com.company.explorecalifornia.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * The interface {@code RoleRepository} specifies methods for operating on the Role entity in the database
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Retrieves a role from the database by role name
     * @param name the role name
     * @return the role
     */
    Optional<Role> findByName(String name);
}
