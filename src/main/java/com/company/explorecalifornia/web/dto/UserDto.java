package com.company.explorecalifornia.web.dto;

import com.company.explorecalifornia.service.validation.FieldMatch;
import com.company.explorecalifornia.service.validation.StrongPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for transferring user attributes between the layers of the application
 *
 */
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "fields must match")
})
public class UserDto {

    private Long id;

    @NotNull(message = "field is required")
    @Size(min = 4, max = 15, message = "must be between 4 and 15 characters")
    private String username;

    @NotNull(message = "field is required")                 // adminR1++
    @StrongPassword
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String matchingPassword;

    @NotNull(message = "field is required")
    @Size(min = 2, max = 15, message = "must be between 2 and 15 characters")
    private String firstName;

    @NotNull(message = "field is required")
    @Size(min = 2, max = 15, message = "must be between 2 and 15 characters")
    private String lastName;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}