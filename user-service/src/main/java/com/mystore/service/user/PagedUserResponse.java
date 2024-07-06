package com.mystore.service.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagedUserResponse {

    private Long totalUsers;
    private Long numberOfUsers;
    private Integer page;
    private Long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<UserInfo> users;

    public PagedUserResponse(List<UserInfo> users) {
        this((long) users.size(), (long) users.size(), 1, Long.MAX_VALUE, false, false, users);
    }

    public PagedUserResponse(Long totalUsers, Long numberOfUsers, Integer page, Long size, List<UserInfo> users) {
        this(totalUsers, numberOfUsers, page, size, page * size < totalUsers, page > 1, users);
    }
}
