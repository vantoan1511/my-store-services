package com.mystore.service.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
//@Authenticated
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public List<UserInfo> getAll() {
        return userService.getAll();
    }

    @GET
    @Path("/{id}")
//    @RolesAllowed({"admin"})
    public UserInfo getById(@PathParam("id") Long id) {
        return userService.getById(id);
    }

}
