package com.mystore.user.entity;

import com.mystore.user.UserException;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends PanacheEntity implements Comparable<UserInfo> {

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

    @Column
    @JoinColumn(name = "avatar_id")
    private Long avatarId;

    @Transactional
    public static void save(UserInfo userInfo) {
        UserInfo savedUserInfo = (UserInfo) findByIdOptional(userInfo.id)
                .orElseThrow(() -> new UserException("Not found", Response.Status.NOT_FOUND));
        savedUserInfo.setAvatarId(userInfo.getAvatarId());
        savedUserInfo.persist();
    }

    @Override
    public int compareTo(@Nullable UserInfo other) {
        if (other == null) return 1;
        if (other.firstName == null && this.firstName == null) return 0;
        if (other.firstName == null) return 1;
        if (this.firstName == null) return -1;
        return this.firstName.compareTo(other.firstName);
    }

}
