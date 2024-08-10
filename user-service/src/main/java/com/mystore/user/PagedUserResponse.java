package com.mystore.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedUserResponse {

    private Long totalUsers;
    private Long numberOfUsers;
    private Integer page;
    private Long size;
    private boolean hasNext;
    private boolean hasPrevious;
    private List<User> users;

    public PagedUserResponse(List<User> users) {
        this((long) users.size(), (long) users.size(), 1, Long.MAX_VALUE, false, false, users);
    }

    public PagedUserResponse(Long totalUsers, Long numberOfUsers, Integer page, Long size, List<User> users) {
        this(totalUsers, numberOfUsers, page, size, page * size < totalUsers, page > 1, users);
    }

    public static PagedUserResponse from(List<User> users, PageRequest pageRequest) {
        var pagedUsers = users.stream()
                .skip((pageRequest.getPage() - 1) * pageRequest.getSize())
                .limit(pageRequest.getSize())
                .toList();
        return new PagedUserResponse(
                (long) users.size(), (long) pagedUsers.size(), pageRequest.getPage(), pageRequest.getSize(), pagedUsers);
    }

}
