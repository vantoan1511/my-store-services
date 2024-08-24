package com.shopbee.user;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class UserMapper {

    public static UserRepresentation toUserRepresentation(UserCreation userCreation) {
        UserRepresentation user = new UserRepresentation();

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(userCreation.getPassword());

        user.setCredentials(List.of(passwordCredential));
        user.setUsername(userCreation.getUsername());
        user.setEmail(userCreation.getEmail());
        user.setEnabled(userCreation.isEnabled());
        user.setEmailVerified(userCreation.isEmailVerified());

        return user;
    }

    public static UserRepresentation toUserRepresentation(UserUpdate userUpdate) {
        UserRepresentation user = new UserRepresentation();

        user.setEmail(userUpdate.getEmail());
        user.setEnabled(userUpdate.isEnabled());
        user.setEmailVerified(userUpdate.isEmailVerified());

        return user;
    }

    public static User toUser(UserCreation userCreation) {
        User user = new User();
        user.setUsername(userCreation.getUsername());
        user.setEmail(userCreation.getEmail());
        user.setFirstName(userCreation.getFirstName());
        user.setLastName(userCreation.getLastName());
        user.setGender(Gender.UNKNOWN);
        return user;
    }
}
