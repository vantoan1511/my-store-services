package com.mystore.service.userservice;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
// @Authenticated
public class UserResource {

    @GET
    public List<User> getAllUsers() {
        return List.of(
                new User("Toan"),
                new User("Nguyen"));
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "admin", "user" })
    public User getUserInfoById(@PathParam("id") Long id) {
        return new User("Toan");
    }

    private static class User {
        public String name;

        public User(String name) {
            this.name = name;
        }
    }
}
