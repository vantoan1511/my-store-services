package com.mystore.service.user.boundary;

import com.mystore.service.user.PageRequest;
import com.mystore.service.user.control.UserService;
import com.mystore.service.user.entity.UserSortingCriteria;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Response getAll(@BeanParam UserSortingCriteria sortingCriteria,
                           @BeanParam PageRequest pageRequest) {
        return Response
                .ok()
                .entity(userService.getAll(sortingCriteria, pageRequest))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok().entity(userService.getById(id)).build();
    }

}
