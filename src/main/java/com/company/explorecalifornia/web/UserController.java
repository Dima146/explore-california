package com.company.explorecalifornia.web;

import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.service.UserService;
import com.company.explorecalifornia.web.dto.LoginDto;
import com.company.explorecalifornia.web.dto.UserDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class {@code UserController} is a controller that contains REST endpoints
 * for user registration and authentication.
 *
 */
@RestController
public class UserController {

    private final UserService userService;
    private final DtoConverter<User, UserDto> dtoConverter;

    public UserController(UserService userService, DtoConverter<User, UserDto> dtoConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Registers a new user
     *
     * @param userDto - the user to register
     * @return the registered user -with hateoas ---?
    */
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody @Valid UserDto userDto) {
        User savedUser = userService.register(dtoConverter.convertToEntity(userDto));
        return dtoConverter.convertToDto(savedUser);
    }

    /**
     * Authenticates a user
     *
     * @param loginDto - the user to authenticate
     * @return the JWT
    */
    @PostMapping("/authentication")
    @ResponseStatus(HttpStatus.OK)
    public String authenticate(@RequestBody @Valid LoginDto loginDto) {
        return userService.login(loginDto.getUsername(), loginDto.getPassword());
    }
}