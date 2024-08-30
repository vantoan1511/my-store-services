package com.shopbee.service.user;

import lombok.Getter;

import java.util.Comparator;

@Getter
public enum UserSortField {

    FIRST_NAME(Comparator.comparing(User::getFirstName, Comparator.nullsFirst(Comparator.naturalOrder()))),

    LAST_NAME(Comparator.comparing(User::getLastName, Comparator.nullsFirst(Comparator.naturalOrder()))),

    USERNAME(Comparator.comparing(User::getUsername, Comparator.nullsFirst(Comparator.naturalOrder()))),

    EMAIL(Comparator.comparing(User::getEmail, Comparator.nullsFirst(Comparator.naturalOrder()))),

    CREATED_AT(Comparator.comparing(User::getCreatedAt, Comparator.nullsFirst(Comparator.naturalOrder()))),

    DEFAULT(Comparator.comparing(User::getCreatedAt, Comparator.nullsFirst(Comparator.naturalOrder()))
            .thenComparing(User::getFirstName, Comparator.nullsFirst(Comparator.naturalOrder())));

    private final Comparator<User> comparator;

    UserSortField(Comparator<User> comparator) {
        this.comparator = comparator;
    }

}
