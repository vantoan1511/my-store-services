package com.mystore.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserCreation {

    @Length(max = 15, message = "First name exceeds the max length of 15 characters")
    private String firstName;

    @Length(max = 15, message = "Last name exceeds the max length of 15 characters")
    private String lastName;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(max = 25, message = "Username must not exceed 25 characters of length")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private boolean enabled;

    private boolean emailVerified;

}
