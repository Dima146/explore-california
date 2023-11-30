package com.company.explorecalifornia.service.impl;

import com.company.explorecalifornia.domain.Role;
import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.exception.DuplicateEntityException;
import com.company.explorecalifornia.exception.NoSuchEntityException;
import com.company.explorecalifornia.security.jwt.JWTAuthProvider;
import com.company.explorecalifornia.repository.RoleRepository;
import com.company.explorecalifornia.repository.UserRepository;
import com.company.explorecalifornia.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The class {@code UserServiceImpl} is an implementation of the {@link UserService} interface
 * and is designed to handle objects of the {@link User} class.
 *
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTAuthProvider jwtAuthProvider;
    private final AuthenticationManager authenticationManager;

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder, JWTAuthProvider jwtAuthProvider,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthProvider = jwtAuthProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public String login(String username, String password) {
        LOGGER.info("User with the username: {} is attempting to log in", username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User with the username " + username + " does not exist");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtAuthProvider.generateToken(username);
    }

    @Override
    @Transactional
    public User register(User user) {
        String username = user.getUsername();
        LOGGER.info("Create a new user with the username {}", username);
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new DuplicateEntityException("User with the username " + username + " already exists");
        }
        Optional<Role> role = roleRepository.findByName(DEFAULT_ROLE);
        if (role.isEmpty()) {
            throw new NoSuchEntityException("Role with name " + DEFAULT_ROLE + " was not found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(role.get()));
        return userRepository.save(user);
    }
}