package com.mystore.user.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Comparator;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends PanacheEntity implements Comparable<UserInfo> {

    @Column(name = "first_name", length = 15)
    @Size(max = 15)
    private String firstName;

    @Column(name = "last_name", length = 15)
    @Size(max = 15)
    private String lastName;

    @Column(name = "username", length = 15, unique = true, nullable = false)
    @NotBlank
    @Size(max = 15)
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    @Column(name = "phone", length = 10)
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @JoinColumn(name = "avatar_id")
    private Long avatarId;

    @Override
    public int compareTo(@Nullable UserInfo other) {
        if (other == null) return 1;
        return Comparator.nullsFirst(String::compareTo)
                .compare(this.firstName, other.firstName);
    }

}
