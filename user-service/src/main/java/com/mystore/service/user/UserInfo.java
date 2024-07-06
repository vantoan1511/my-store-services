package com.mystore.service.user;

import jakarta.annotation.Nullable;
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
public class UserInfo implements Comparable<UserInfo> {

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

    @Override
    public int compareTo(@Nullable UserInfo other) {
        if (other == null) return 1;
        if (other.firstName == null && this.firstName == null) return 0;
        if (other.firstName == null) return 1;
        if (this.firstName == null) return -1;
        return this.firstName.compareTo(other.firstName);
    }
}
