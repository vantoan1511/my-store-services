package com.mystore.user.avatar;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;

@Path("/api/avatars")
@Slf4j
public class AvatarResource {

    @Inject
    AvatarService avatarService;

    @GET
    @Path("{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getById(@RestForm Long id) {
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@RestForm Long userId, @RestForm("avatar") FileUpload avatar) throws IOException {
        avatarService.save(userId, avatar);
        return Response.ok("Upload avatar successfully").build();
    }

}
