package com.mystore.user.control;

import com.mystore.user.PageRequest;
import com.mystore.user.entity.PagedUserResponse;
import com.mystore.user.UserException;
import com.mystore.user.entity.UserInfo;
import com.mystore.user.entity.UserSortingCriteria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public PagedUserResponse getAll(UserSortingCriteria userSortingCriteria, @Valid PageRequest pageRequest) {
        var users = userRepository.listAll();
        var sortedUsers = sort(users, userSortingCriteria);
        return PagedUserResponse.from(sortedUsers, pageRequest);
    }

    private List<UserInfo> sort(List<UserInfo> users, UserSortingCriteria sortingCriteria) {
        var sortedUsers = users.stream()
                .sorted(sortingCriteria.getField())
                .toList();
        return sortingCriteria.isDescending() ? sortedUsers.reversed() : sortedUsers;
    }

    public UserInfo getById(Long id) {
        return (UserInfo) UserInfo.findByIdOptional(id)
                .orElseThrow(() -> new UserException("User with ID " + id + " not found", Response.Status.NOT_FOUND));
    }

    public UserInfo getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserException("User with USERNAME " + username + " not found.", Response.Status.NOT_FOUND));
    }

    public UserInfo getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UserException("User with EMAIL " + email + " not found.", Response.Status.NOT_FOUND));
    }

    public void save(UserInfo userInfo) {
        UserInfo.save(userInfo);
    }
}
