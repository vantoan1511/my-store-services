package com.mystore.user.boundary;

import com.mystore.user.PageRequest;
import com.mystore.user.control.UserService;
import com.mystore.user.entity.UserSortingCriteria;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"ADMIN"})
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Response getAll(@BeanParam UserSortingCriteria sortingCriteria,
                           @BeanParam PageRequest pageRequest) {
        return Response.ok().entity(userService.getAll(sortingCriteria, pageRequest)).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok().entity(userService.getById(id)).build();
    }

}
