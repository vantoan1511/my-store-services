package com.mystore.user;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Comparator;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Comparable<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Override
    public int compareTo(@Nullable User other) {
        if (other == null) return 1;
        return Comparator.nullsFirst(Comparator.<Timestamp>naturalOrder())
                .compare(this.createdAt, other.createdAt);
    }

}
