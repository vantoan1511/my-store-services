package com.mystore.service.user.control;

import com.mystore.service.user.*;
import com.mystore.service.user.entity.PagedUserResponse;
import com.mystore.service.user.entity.UserException;
import com.mystore.service.user.entity.UserInfo;
import com.mystore.service.user.entity.UserSortingCriteria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        return userRepository
                .findByIdOptional(id)
                .orElseThrow(() ->
                        new UserException("User with ID " + id + " not found.", Response.Status.NOT_FOUND));
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

}
