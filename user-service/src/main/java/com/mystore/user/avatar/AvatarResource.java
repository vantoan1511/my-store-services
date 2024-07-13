package com.mystore.user.avatar;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.net.URI;

@Path("/api/avatars")
public class AvatarResource {

    @Inject
    AvatarService avatarService;

    @GET
    @Path("{id}")
    @Produces({"image/jpeg", "image/png"})
    public Response getById(@RestPath Long id) {
        var avatar = avatarService.getById(id);
        return Response.ok(avatar.getData())
                .header("Content-Disposition", "attachment; filename=\"" + avatar.getName() + "\"")
                .build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@RestForm Long userId,
                           @RestForm("avatar") FileUpload avatar,
                           @Context UriInfo uriInfo) {
        Avatar savedAvatar = avatarService.save(userId, avatar);
        URI uri = uriInfo.getAbsolutePathBuilder().path(savedAvatar.id.toString()).build();
        return Response.created(uri).build();
    }

}
