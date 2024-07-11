package com.mystore.user.avatar;


import jakarta.ws.rs.FormParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jboss.resteasy.reactive.PartType;

@Getter
@Setter
@NoArgsConstructor
public class AvatarUploadForm {

    @FormParam("fileName")
    @PartType("text/plain")
    private String fileName;

    @FormParam("file")
    @PartType("application/octet-stream")
    private byte[] data;
}
