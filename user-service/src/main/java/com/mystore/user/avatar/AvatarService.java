package com.mystore.user.avatar;

import com.mystore.user.UserException;
import com.mystore.user.control.UserService;
import com.mystore.user.entity.UserInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;

@ApplicationScoped
public class AvatarService {

    @Inject
    UserService userService;

    public Avatar getById(Long id) {
        return Avatar.findById(id);
    }

    public void save(Long userId, FileUpload avatarFile) {
        try {
            Avatar avatar =
                    Avatar.save(avatarFile.fileName(), Files.readAllBytes(avatarFile.uploadedFile()));

            UserInfo userInfo = userService.getById(userId);
            userInfo.setAvatarId(avatar.id);
            UserInfo.save(userInfo);
        } catch (IOException e) {
            throw new UserException("Save avatar failed", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
