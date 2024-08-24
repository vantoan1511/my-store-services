package com.shopbee.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@Transactional
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    KeycloakService keycloakService;

    public User getById(Long id) {
        return userRepository.findByIdOptional(id)
                .orElseThrow(() ->
                        new UserException("User with ID " + id + " not found", Response.Status.NOT_FOUND));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserException("User with USERNAME " + username + " not found.", Response.Status.NOT_FOUND));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserException("User with EMAIL " + email + " not found.", Response.Status.NOT_FOUND));
    }

    public PagedUserResponse getAll(UserSort userSort, @Valid PageRequest pageRequest) {
        List<User> users = userRepository.listAll();
        List<User> sortedUsers = sort(users, userSort);
        return PagedUserResponse.from(sortedUsers, pageRequest);
    }

    private List<User> sort(List<User> users, UserSort userSort) {
        List<User> sortedUsers = users.stream()
                .sorted(userSort.getSortField().getComparator())
                .toList();
        if (userSort.isDescending()) {
            return sortedUsers.reversed();
        }
        return sortedUsers;
    }

    public User update(Long id, @Valid UserUpdate userUpdate) {
        User user = getById(id);
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setEmail(userUpdate.getEmail());
        return user;
    }

    public User register(@Valid UserCreation userCreation) {
        validateUniqueUsernameAndEmail(userCreation.getUsername(), userCreation.getEmail());
        linkNewUserToKeycloak(userCreation);
        User newUser = UserMapper.toUser(userCreation);
        userRepository.persist(newUser);
        return newUser;
    }

    private void validateUniqueUsernameAndEmail(String username, String email) {
        userRepository.findByUsernameOrEmail(username, email).ifPresent((user) -> {
            if (user.getUsername().equals(username)) {
                throw new UserException("Username " + username + " existed", Response.Status.CONFLICT);
            }
            throw new UserException("Email " + email + " has associated with another account", Response.Status.CONFLICT);
        });
    }

    public void linkNewUserToKeycloak(UserCreation userCreation) {
        try (Response response = keycloakService.create(userCreation)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                ErrorResponse keycloakResponse = response.readEntity(ErrorResponse.class);
                throw new UserException(keycloakResponse.getErrorMessage(), response.getStatus());
            }
        }
    }

}
