package com.mystore.user.avatar;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avatars")
public class Avatar extends PanacheEntity {

    @Column(nullable = false)
    private String name;

    @Lob
    @Basic
    private byte[] data;

}
