package com.shopbee.service.user;

import com.shopbee.service.KeycloakService;
import com.shopbee.service.PageRequest;
import com.shopbee.service.UserException;
import com.shopbee.service.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@Transactional
public class UserService {

    UserRepository userRepository;

    KeycloakService keycloakService;

    public UserService(UserRepository userRepository, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.keycloakService = keycloakService;
    }

    public PagedUserResponse getAll(UserSort userSort, PageRequest pageRequest) {
        List<User> users = userRepository.listAll();
        List<User> sortedUsers = sort(users, userSort);
        return PagedUserResponse.from(sortedUsers, pageRequest);
    }

    private List<User> sort(List<User> users, UserSort userSort) {
        List<User> sortedUsers = users.stream().sorted(userSort.getSortField().getComparator()).toList();
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

    public User createNew(UserCreation userCreation) {
        if (userCreation == null) {
            throw new UserException("Please provide all required information to create an account", Response.Status.BAD_REQUEST);
        }

        keycloakService.createUser(userCreation);

        User newUser = UserMapper.toUser(userCreation);
        userRepository.persist(newUser);
        return newUser;
    }

    public void update(Long id, UserUpdate userUpdate) {
        validateUniqueEmailUpdate(id, userUpdate.getEmail());

        User user = getById(id);
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setEmail(userUpdate.getEmail());

        keycloakService.updateUser(user.getUsername(), userUpdate);
    }

    private void validateUniqueEmailUpdate(Long id, String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (!user.getId().equals(id)) {
                throw new UserException("Email " + email + " has associated with another account", Response.Status.CONFLICT);
            }
        });
    }

    public void delete(List<Long> ids) {
        ids.forEach(this::delete);
    }

    private void delete(Long id) {
        String username = getById(id).getUsername();
        keycloakService.delete(username);
        userRepository.delete("id", id);
    }

}
