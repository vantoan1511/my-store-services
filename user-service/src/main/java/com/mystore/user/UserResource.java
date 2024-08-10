package com.mystore.user;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

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
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(userService.getById(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(UserCreation userCreation, @Context UriInfo uriInfo) {
        User savedUser = userService.register(userCreation);
        URI uri = uriInfo.getAbsolutePathBuilder().path(savedUser.id.toString()).build();
        return Response.created(uri).entity(savedUser).build();
    }
}
