package com.shopbee.userservice;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("api/health-check")
@PermitAll
public class HealthResource {

    @GET
    public Response checkHealth() {
        return Response.ok("Service is running").build();
    }
}