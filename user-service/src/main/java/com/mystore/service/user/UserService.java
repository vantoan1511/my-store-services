package com.mystore.service.user;

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
        var pagedUsers = users.stream()
                .skip((pageRequest.getPage() - 1) * pageRequest.getSize())
                .limit(pageRequest.getSize())
                .toList();
        return new PagedUserResponse((long) users.size(), (long) pagedUsers.size(), pageRequest.getPage(), pageRequest.getSize(), pagedUsers);
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
