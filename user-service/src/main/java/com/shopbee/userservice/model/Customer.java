package com.shopbee.userservice.model;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
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
}
