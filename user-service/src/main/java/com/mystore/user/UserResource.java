package com.mystore.user;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestPath;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Response getAll(@BeanParam UserSort sortingCriteria,
                           @BeanParam PageRequest pageRequest) {
        return Response.ok(userService.getAll(sortingCriteria, pageRequest)).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@RestPath Long id) {
        return Response.ok(userService.getById(id)).build();
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserCreation userCreation) {
        return userService.create(userCreation);
    }
}
