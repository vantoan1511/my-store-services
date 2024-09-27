package com.shopbee.userservice.service;

import com.shopbee.userservice.entity.User;
import com.shopbee.userservice.exception.UserException;
import com.shopbee.userservice.mapper.UserMapper;
import com.shopbee.userservice.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;

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

    public PagedResponse<UserDetails> getAll(UserSort userSort, PageRequest pageRequest) {
        List<UserDetails> users = UserMapper.toUserDetailsList(userRepository.listAll())
                .stream()
                .map(this::withUserStatus)
                .toList();
        List<UserDetails> sortedUsers = sort(users, userSort);
        return PagedResponse.from(sortedUsers, pageRequest);
    }

    public UserDetails getDetailsById(Long id) {
        User user = getById(id);
        UserRepresentation keycloakUser = keycloakService.getUserByUsername(user.getUsername());
        return UserMapper.withAccountStatus(user, keycloakUser);
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

    public void resetPassword(Long id, PasswordReset passwordReset) {
        String username = getById(id).getUsername();
        keycloakService.resetPassword(username, passwordReset);
    }

    private UserDetails withUserStatus(UserDetails userDetails) {
        UserRepresentation userKeycloak = keycloakService.getUserByUsername(userDetails.getUsername());
        userDetails.setEnabled(userKeycloak.isEnabled());
        userDetails.setEmailVerified(userKeycloak.isEmailVerified());
        return userDetails;
    }

    private List<UserDetails> sort(List<UserDetails> users, UserSort userSort) {
        List<UserDetails> sortedUsers = users.stream().sorted(userSort.getSortField().getComparator()).toList();
        if (userSort.isDescending()) {
            return sortedUsers.reversed();
        }
        return sortedUsers;
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
