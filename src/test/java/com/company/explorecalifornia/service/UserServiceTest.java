package com.company.explorecalifornia.service;

import com.company.explorecalifornia.domain.Role;
import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.security.jwt.JWTAuthProvider;
import com.company.explorecalifornia.repository.RoleRepository;
import com.company.explorecalifornia.repository.UserRepository;
import com.company.explorecalifornia.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTAuthProvider jwtAuthProvider;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Test
    public void givenUserCredentials_login_thenReturnJWT() {
        String username = "admin";
        String password = "adminR1++";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
        String token = userService.login(username, password);
        System.out.println(token);
    }

    @Test
    public void givenUser_registerUser_thenReturnCreatedUser() {
        User user = new User("admin", "adminR1++");
        Role role = new Role(DEFAULT_ROLE);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(roleRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(role));

        User registered = userService.register(user);
        assertThat(registered).isNotNull();
    }
}