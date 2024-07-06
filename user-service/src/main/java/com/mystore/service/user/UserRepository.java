package com.mystore.service.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserInfo> {

    public Optional<UserInfo> findByUsername(String username) {
        return find("username", username)
                .firstResultOptional();
    }

    public Optional<UserInfo> findByEmail(String email) {
        return find("email", email)
                .firstResultOptional();
    }

}

