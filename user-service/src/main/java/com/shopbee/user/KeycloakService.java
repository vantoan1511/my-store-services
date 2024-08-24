package com.shopbee.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class KeycloakService {

    private static final String REALM = "shopbee";

    @Inject
    Keycloak keycloak;

    public Response create(@Valid UserCreation userCreation) {
        if (userCreation == null) {
            throw new UserException("Please provide all required information to create an account", Response.Status.BAD_REQUEST);
        }

        UserRepresentation user = UserMapper.toUserRepresentation(userCreation);
        UsersResource usersResource = keycloak.realm(REALM).users();
        return usersResource.create(user);
    }

    public Response delete(String username) {
        try {
            UsersResource usersResource = keycloak.realm(REALM).users();
            List<UserRepresentation> users = usersResource.searchByUsername(username, true);
            UserRepresentation userRepresentation = users.getFirst();
            return usersResource.delete(userRepresentation.getId());
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
