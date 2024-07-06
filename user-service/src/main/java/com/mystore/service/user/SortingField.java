package com.mystore.service.user;

import java.util.Comparator;

public enum SortingField implements Comparator<UserInfo> {

    FIRST_NAME,
    LAST_NAME {
        @Override
        public int compare(UserInfo thisUser, UserInfo thatUser) {
            return thisUser.getLastName().compareTo(thatUser.getLastName());
        }
    },
    EMAIL {
        @Override
        public int compare(UserInfo thisUser, UserInfo thatUser) {
            return thisUser.getEmail().compareTo(thatUser.getEmail());
        }
    },
    USERNAME {
        @Override
        public int compare(UserInfo thisUser, UserInfo thatUser) {
            return thisUser.getUsername().compareTo(thatUser.getUsername());
        }
    },
    GENDER {
        @Override
        public int compare(UserInfo thisUser, UserInfo thatUser) {
            return thisUser.getGender().name().compareTo(thatUser.getGender().name());
        }
    };

    SortingField() {
    }

    @Override
    public int compare(UserInfo thisUser, UserInfo thatUser) {
        return thisUser.compareTo(thatUser);
    }
}
