package com.mystore.user.control;

import com.mystore.user.PageRequest;
import com.mystore.user.UserException;
import com.mystore.user.entity.PagedUserResponse;
import com.mystore.user.entity.UserInfo;
import com.mystore.user.entity.UserSortingCriteria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {

    public PagedUserResponse getAll(UserSortingCriteria userSortingCriteria, @Valid PageRequest pageRequest) {
        List<UserInfo> users = UserInfo.listAll();
        List<UserInfo> sortedUsers = sort(users, userSortingCriteria);
        return PagedUserResponse.from(sortedUsers, pageRequest);
    }

    private List<UserInfo> sort(List<UserInfo> users, UserSortingCriteria sortingCriteria) {
        var sortedUsers = users.stream()
                .sorted(sortingCriteria.getField())
                .toList();
        return sortingCriteria.isDescending() ? sortedUsers.reversed() : sortedUsers;
    }

    @Transactional
    public void update(Long id, Long avatarId) {
        UserInfo userInfo = getById(id);
        userInfo.setAvatarId(avatarId);
        userInfo.persist();
    }

    public UserInfo getById(Long id) {
        return (UserInfo) UserInfo.findByIdOptional(id)
                .orElseThrow(() -> new UserException("User with ID " + id + " not found", Response.Status.NOT_FOUND));
    }

    public UserInfo getByUsername(String username) {
        return (UserInfo) UserInfo.find("username", username)
                .firstResultOptional()
                .orElseThrow(() ->
                        new UserException("User with USERNAME " + username + " not found.", Response.Status.NOT_FOUND));
    }

    public UserInfo getByEmail(String email) {
        return (UserInfo) UserInfo.find("email", email)
                .firstResultOptional()
                .orElseThrow(
                        () -> new UserException("User with EMAIL " + email + " not found.", Response.Status.NOT_FOUND));
    }

}
