package com.shopbee.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phone;

    private String address;

    private String avatarUrl;

    private Gender gender;

    private Timestamp createdAt;

    private Timestamp modifiedAt;

    private boolean enabled;

    private boolean emailVerified;
}
