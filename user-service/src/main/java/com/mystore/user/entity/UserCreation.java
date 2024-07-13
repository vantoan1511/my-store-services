package com.mystore.user.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreation {

    private String firstName;

    private String lastName;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(max = 15, message = "Username must not exceed 15 characters of length")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}
