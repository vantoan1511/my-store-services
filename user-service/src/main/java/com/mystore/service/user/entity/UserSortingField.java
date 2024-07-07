package com.mystore.service.user.entity;

import java.util.Comparator;

public enum UserSortingField implements Comparator<UserInfo> {

    FIRST_NAME(Comparator.comparing(UserInfo::getFirstName)),
    LAST_NAME(Comparator.comparing(UserInfo::getLastName)),
    EMAIL(Comparator.comparing(UserInfo::getEmail)),
    USERNAME(Comparator.comparing(UserInfo::getUsername)),
    GENDER(Comparator.comparing(user -> user.getGender().name()));

    private final Comparator<UserInfo> comparator;

    UserSortingField(Comparator<UserInfo> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(UserInfo thisUser, UserInfo thatUser) {
        return comparator.compare(thisUser, thatUser);
    }
}
