package com.shopbee.service.customer;

import com.shopbee.service.KeycloakService;
import com.shopbee.service.UserException;
import com.shopbee.service.UserMapper;
import com.shopbee.service.user.User;
import com.shopbee.service.user.UserRepository;
import com.shopbee.service.user.UserService;
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

    public CustomerService(UserRepository userRepository, UserService userService, KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.keycloakService = keycloakService;
    }

    public Customer getByUsername(String username) {
        return UserMapper.toCustomer(userService.getByUsername(username));
    }

    public void updateProfile(String username, @Valid CustomerUpdate customerUpdate) {
        //email, firstname, lastname, gender, phone, address, avatar
        validateUniqueEmail(username, customerUpdate.getEmail());
        User user = userService.getByUsername(username);
        user.setEmail(customerUpdate.getEmail());
        user.setFirstName(customerUpdate.getFirstName());
        user.setLastName(customerUpdate.getLastName());
        user.setAddress(customerUpdate.getAddress());
        user.setAvatarUrl(customerUpdate.getAvatarUrl());
        user.setGender(customerUpdate.getGender());
        user.setPhone(customerUpdate.getPhone());
    }

    private void validateUniqueEmail(String username, String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (!user.getUsername().equals(username)) {
                throw new UserException("Email " + email + " has associated with another account", Response.Status.CONFLICT);
            }
        });
    }

    public void updatePassword() {
        //update pw
    }

    public void resetPassword() {
        //forgot pw -> send email to reset pw
    }

    public Customer register(@Valid CustomerRegistration customerRegistration) {
        keycloakService.createUser(UserMapper.toUserRepresentation(customerRegistration));
        User user = UserMapper.toUser(customerRegistration);
        userRepository.persist(user);
        return UserMapper.toCustomer(user);
    }


}
