package com.shopbee.service;

import com.shopbee.service.user.UserCreation;
import com.shopbee.service.user.UserUpdate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class KeycloakService {

    private static final String REALM = "shopbee";

    private final Keycloak keycloak;

    private final UsersResource usersResource;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
        this.usersResource = keycloak.realm(REALM).users();
    }

    public void createUser(UserCreation userCreation) {
        UserRepresentation user = UserMapper.toUserRepresentation(userCreation);
        createUser(user);
    }

    public void createUser(UserRepresentation userRepresentation) {
        try (Response response = usersResource.create(userRepresentation)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                ErrorResponse keycloakResponse = response.readEntity(ErrorResponse.class);
                throw new UserException(keycloakResponse.getErrorMessage(), response.getStatus());
            }
        }
    }

    public void updateUser(String username, UserUpdate userUpdate) {
        UserRepresentation user = UserMapper.bind(getUserByUsername(username), userUpdate);
        UserResource userResource = getUserResourceByUsername(username);
        userResource.update(user);
    }

    public void delete(String username) {
        String userId = getUserByUsername(username).getId();
        try (Response response = usersResource.delete(userId)) {
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new UserException("User does not associated with any Keycloak user", response.getStatus());
            }
        }
    }

    public void resetPassword(String username, PasswordReset passwordReset) {
        UserResource userResource = getUserResourceByUsername(username);

        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(passwordReset.getPassword());
        passwordCredential.setTemporary(passwordReset.isTemporary());

        userResource.resetPassword(passwordCredential);
    }

    private UserResource getUserResourceByUsername(String username) {
        String userId = getUserByUsername(username).getId();
        return usersResource.get(userId);
    }

    private UserRepresentation getUserByUsername(String username) {
        List<UserRepresentation> users = usersResource.searchByUsername(username, true);
        if (CollectionUtil.isEmpty(users)) {
            throw new UserException("User with username " + username + " not found", Response.Status.NOT_FOUND);
        }
        return users.getFirst();
    }

}
