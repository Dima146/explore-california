package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.User;

/**
 * The {@code UserService} interface specifies the behavior for handling {@link User} objects.
 */
public interface UserService {

    /**
     * Creates a new user
     *
     * @param user the user to create
     * @return the created user
     */
    User register(User user);

    /**
     * Logs in to the application
     *
     * @param username the username
     * @param password  the password
     * @return the generated JWT
     */
    String login(String username, String password);
}