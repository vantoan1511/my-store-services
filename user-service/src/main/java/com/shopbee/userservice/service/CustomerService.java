package com.shopbee.userservice.service;

import com.shopbee.userservice.exception.UserException;
import com.shopbee.userservice.mapper.UserMapper;
import com.shopbee.userservice.model.Customer;
import com.shopbee.userservice.model.CustomerRegistration;
import com.shopbee.userservice.model.CustomerUpdate;
import com.shopbee.userservice.model.PasswordUpdate;
import com.shopbee.userservice.model.User;
import com.shopbee.userservice.model.UserRepository;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Transactional
public class CustomerService {

    UserRepository userRepository;

    UserService userService;

    KeycloakService keycloakService;

    SecurityIdentity securityIdentity;

    public CustomerService(UserRepository userRepository,
                           UserService userService,
                           KeycloakService keycloakService,
                           SecurityIdentity securityIdentity) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.keycloakService = keycloakService;
        this.securityIdentity = securityIdentity;
    }

    public Customer getByUsername(String username) {
        return UserMapper.toCustomer(userService.getByUsername(username));
    }

    public Customer register(CustomerRegistration customerRegistration) {
        keycloakService.createUser(customerRegistration);
        User user = UserMapper.toUser(customerRegistration);
        userRepository.persist(user);
        return UserMapper.toCustomer(user);
    }

    public void updateProfile(String username, @Valid CustomerUpdate customerUpdate) {
        validateUniqueEmailUpdate(username, customerUpdate.getEmail());
        User user = userService.getByUsername(username);
        user.setEmail(customerUpdate.getEmail());
        user.setFirstName(customerUpdate.getFirstName());
        user.setLastName(customerUpdate.getLastName());
        user.setAddress(customerUpdate.getAddress());
        user.setAvatarUrl(customerUpdate.getAvatarUrl());
        user.setGender(customerUpdate.getGender());
        user.setPhone(customerUpdate.getPhone());
    }

    public void updatePassword(String username, PasswordUpdate passwordUpdate) {
        if (passwordUpdate == null) {
            throw new UserException("Password update request is invalid", Response.Status.BAD_REQUEST);
        }
        keycloakService.updatePassword(username, passwordUpdate);
    }

    private void validateUniqueEmailUpdate(String username, String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (!user.getUsername().equals(username)) {
                throw new UserException("Email " + email + " has associated with another account", Response.Status.CONFLICT);
            }
        });
    }

}
