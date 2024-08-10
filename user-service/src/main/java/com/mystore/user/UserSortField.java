package com.mystore.user;

import lombok.Getter;

import java.util.Comparator;

@Getter
public enum UserSortField {

    CREATED_AT(Comparator.comparing(User::getCreatedAt, Comparator.nullsFirst(Comparator.naturalOrder()))),

    DEFAULT(Comparator.comparing(User::getCreatedAt, Comparator.nullsFirst(Comparator.naturalOrder()))
            .thenComparing(User::getFirstName, Comparator.nullsFirst(Comparator.naturalOrder())));

    private final Comparator<User> comparator;

    UserSortField(Comparator<User> comparator) {
        this.comparator = comparator;
    }

}
