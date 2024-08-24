package com.shopbee.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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

    @Inject
    Keycloak keycloak;

    public void create(UserCreation userCreation) {
        if (userCreation == null) {
            throw new UserException("Please provide all required information to create an account", Response.Status.BAD_REQUEST);
        }

        UserRepresentation user = UserMapper.toUserRepresentation(userCreation);
        UsersResource usersResource = keycloak.realm(REALM).users();
        try (Response response = usersResource.create(user)) {
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                ErrorResponse keycloakResponse = response.readEntity(ErrorResponse.class);
                throw new UserException(keycloakResponse.getErrorMessage(), response.getStatus());
            }
        } catch (Exception e) {
            throw new UserException("An error occurred from authentication server", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public void update(String username, UserUpdate userUpdate) {
        UserResource userResource = getUserResourceByUsername(username);
        UserRepresentation user = UserMapper.bind(getUserByUsername(username), userUpdate);
        userResource.update(user);
    }

    public void delete(String username) {
        UserRepresentation userRepresentation = getUserByUsername(username);
        try (Response response = getUsersResource().delete(userRepresentation.getId())) {
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new UserException("User does not associated with any Keycloak user", response.getStatus());
            }
        } catch (Exception e) {
            throw new UserException("An error occurred from authentication server", Response.Status.INTERNAL_SERVER_ERROR);
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
        return getUsersResource().get(getUserByUsername(username).getId());
    }

    private UserRepresentation getUserByUsername(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> users = usersResource.searchByUsername(username, true);
        if (CollectionUtil.isEmpty(users)) {
            throw new UserException("User not found", Response.Status.NOT_FOUND);
        }
        return users.getFirst();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(REALM).users();
    }
}
