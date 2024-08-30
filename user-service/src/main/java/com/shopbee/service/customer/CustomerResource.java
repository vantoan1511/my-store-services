package com.shopbee.service.customer;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("api/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    @Path("{username}")
    @Authenticated
    public Response getByUsername(@PathParam("username") String username) {
        return Response.ok(customerService.getByUsername(username)).build();
    }

    @POST
    @PermitAll
    public Response register(CustomerRegistration customerRegistration, @Context UriInfo uriInfo) {
        Customer customer = customerService.register(customerRegistration);
        URI uri = uriInfo.getAbsolutePathBuilder().path(customer.getUsername()).build();
        return Response.created(uri).entity(customerRegistration).build();
    }

    @PUT
    @Path("{username}")
    @Authenticated
    public Response updateProfile(@PathParam("username") String username, CustomerUpdate customerUpdate) {
        customerService.updateProfile(username, customerUpdate);
        return Response.noContent().build();
    }
}
