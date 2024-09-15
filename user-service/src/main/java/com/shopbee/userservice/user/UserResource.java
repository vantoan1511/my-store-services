package com.shopbee.userservice.user;

import com.shopbee.userservice.PageRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({Role.Constants.ADMIN_VALUE})
public class UserResource {

    UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

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
    public Response create(@Valid UserCreation userCreation, @Context UriInfo uriInfo) {
        User savedUser = userService.createNew(userCreation);
        URI uri = uriInfo.getAbsolutePathBuilder().path(savedUser.getId().toString()).build();
        return Response.created(uri).entity(savedUser).build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, @Valid UserUpdate userUpdate) {
        userService.update(id, userUpdate);
        return Response.noContent().build();
    }

    @DELETE
    public Response delete(List<Long> ids) {
        userService.delete(ids);
        return Response.noContent().build();
    }
}