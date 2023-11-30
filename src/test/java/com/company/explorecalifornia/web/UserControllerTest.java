package com.company.explorecalifornia.web;

import com.company.explorecalifornia.Application;
import com.company.explorecalifornia.domain.User;
import com.company.explorecalifornia.service.impl.UserServiceImpl;
import com.company.explorecalifornia.web.dto.LoginDto;
import com.company.explorecalifornia.web.dto.UserDto;
import com.company.explorecalifornia.web.dto.converter.DtoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DtoConverter<User, UserDto> dtoConverter;
    @MockBean
    private UserServiceImpl userService;

    @Test
    public void givenUser_whenRegister_ReturnRegisteredUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("admin");
        userDto.setPassword("admin1R++");
        userDto.setMatchingPassword("admin1R++");
        userDto.setFirstName("Admin");
        userDto.setLastName("Admin");
        User user = dtoConverter.convertToEntity(userDto);

        when(userService.register(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(post("/registration")
                .content(objectMapper.writeValueAsString(userDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(jsonPath("$.firstName", is("Admin")))
                .andExpect(jsonPath("$.lastName", is("Admin")));
        verify(userService, times(1)).register(user);
    }

    @Test
    public void givenUserCredentials_whenAuthenticate_ReturnJWT() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("admin1R++");
        String jwt = "token";
        when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(jwt);

        mockMvc.perform(post("/authentication")
                .content(objectMapper.writeValueAsString(loginDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).login(loginDto.getUsername(), loginDto.getPassword());
    }
}