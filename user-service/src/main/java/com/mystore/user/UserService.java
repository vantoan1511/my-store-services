package com.mystore.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Slf4j
@ApplicationScoped
@Transactional
public class UserService {

    @Inject
    Keycloak keycloak;

    @Inject
    UserRepository userRepository;

    public Response create(@Valid UserCreation userCreation) {
        if (userCreation == null) {
            throw new UserException("Please provide all required information to create an account", Response.Status.BAD_REQUEST);
        }

        UserRepresentation user = UserMapper.toUserRepresentation(userCreation);

        String realm = System.getenv().getOrDefault("KEYCLOAK_REALM", "my-store");
        UsersResource usersResource = keycloak.realm(realm).users();

        return usersResource.create(user);
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


}
