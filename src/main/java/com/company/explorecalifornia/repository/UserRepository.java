package com.company.explorecalifornia.repository;

import com.company.explorecalifornia.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * The interface {@code UserRepository} specifies methods for operating on the User entity in the database
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user from the database by username
     * @param username the username
     * @return the user
     */
    Optional<User> findByUsername(String username);
}