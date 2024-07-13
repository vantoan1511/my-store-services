package com.mystore.user.avatar;

import com.mystore.user.UserException;
import com.mystore.user.control.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
@Slf4j
public class AvatarService {

    @Inject
    UserService userService;

    @Transactional
    public Avatar getById(Long id) {
        return (Avatar) Avatar.findByIdOptional(id)
                .orElseThrow(() -> new UserException("Avatar with ID " + id + " not found", Response.Status.NOT_FOUND));
    }

    @Transactional
    public Avatar save(Long userId, FileUpload avatarUpload) {
        this.validate(avatarUpload);

        Avatar avatar = from(avatarUpload);
        avatar.persist();

        this.userService.update(userId, avatar.id);
        return avatar;
    }

    private void validate(FileUpload avatarUpload) {
        String contentType = avatarUpload.contentType();
        List<String> allowedTypes = List.of("image/jpeg", "image/png");
        if (!allowedTypes.contains(contentType)) {
            throw new UserException("Only image files are allowed", Response.Status.BAD_REQUEST);
        }
    }

    private Avatar from(FileUpload avatarUpload) {
        try (FileInputStream inputStream = new FileInputStream(avatarUpload.uploadedFile().toFile())) {
            return new Avatar(avatarUpload.fileName(), inputStream.readAllBytes());
        } catch (IOException e) {
            throw new UserException("Failed to read upload avatar file.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}