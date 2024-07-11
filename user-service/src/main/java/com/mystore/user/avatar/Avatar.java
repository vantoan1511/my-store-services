package com.mystore.user.avatar;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "avatars")
public class Avatar extends PanacheEntity {

    @Column
    private String name;

    @Lob
    private byte[] data;

    public Avatar(String name, byte[] data) {
        super();
        this.name = name;
        this.data = data;
    }

    @Transactional
    public static Avatar save(String name, byte[] data) {
        Avatar avatar = new Avatar();
        avatar.setName(name);
        avatar.setData(data);
        avatar.persist();
        return avatar;
    }
}
