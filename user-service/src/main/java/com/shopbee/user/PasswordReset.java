package com.shopbee.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordReset {

    @NotBlank(message = "Password is required")
    private String password;

    private boolean temporary;
}
