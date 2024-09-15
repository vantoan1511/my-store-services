package com.shopbee.service.user;

import com.shopbee.service.Gender;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Comparator;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 15)
    private String firstName;

    @Column(name = "last_name", length = 15)
    private String lastName;

    @Column(name = "username", length = 25, unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "phone", length = 10, unique = true)
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at")
    @UpdateTimestamp
    private Timestamp modifiedAt;

    @Override
    public int compareTo(@Nullable User other) {
        if (other == null) return 1;
        return Comparator.nullsFirst(Comparator.<Timestamp>naturalOrder())
                .compare(this.createdAt, other.createdAt);
    }

}
