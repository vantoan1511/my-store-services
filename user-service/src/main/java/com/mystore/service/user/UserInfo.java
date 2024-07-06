package com.mystore.service.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name", length = 15)
    private String firstName;

    @Column(name = "last_name", length = 15)
    private String lastName;

    @Column(name = "username", length = 15, unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
