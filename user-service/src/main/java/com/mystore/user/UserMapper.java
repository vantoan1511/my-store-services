package com.mystore.user;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class UserMapper {

    public static UserRepresentation toUserRepresentation(UserCreation userCreation) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userCreation.getUsername());
        user.setEmail(userCreation.getEmail());
        user.setFirstName(userCreation.getFirstName());
        user.setLastName(userCreation.getLastName());

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(userCreation.getPassword());
        user.setCredentials(List.of(passwordCredential));

        return user;
    }
}
