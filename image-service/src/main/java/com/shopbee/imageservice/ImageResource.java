package com.shopbee.imageservice;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.net.URI;
import java.util.List;

@Path("api/images")
@Authenticated
public class ImageResource {

    SecurityIdentity securityIdentity;

    ImageService imageService;

    public ImageResource(SecurityIdentity securityIdentity, ImageService imageService) {
        this.securityIdentity = securityIdentity;
        this.imageService = imageService;
    }

    @GET
    public List<Image> getAll() {
        return imageService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces({"image/jpeg"})
    public byte[] getById(@PathParam("id") Long id) {
        return imageService.getById(id);
    }

    @POST
    public Response upload(
            @RestForm("altText") String altText,
            @RestForm("image") FileUpload imageFile,
            @Context UriInfo uriInfo) {
        Image image = imageService.upload(altText, imageFile);
        URI uri = uriInfo.getAbsolutePathBuilder().build(image.getId());
        return Response.created(uri).entity(image).build();
    }
}
