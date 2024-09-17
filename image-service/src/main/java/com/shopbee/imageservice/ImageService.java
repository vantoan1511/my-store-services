package com.shopbee.imageservice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class ImageService {

    ImageRepository imageRepository;

    UserService userService;

    @RestClient
    UserResource userResource;

    public ImageService(ImageRepository imageRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
    }

    public List<Image> getAll() {
        return imageRepository.listAll();
    }

    public byte[] getById(Long id) {
        Image image = imageRepository.findById(id);
        if (image == null) {
            throw new ImageException("Image not found", Response.Status.NOT_FOUND);
        }
        return image.getContent();
    }

    public Image upload(String altText, FileUpload imageFile) {
        try {
            User user = userService.getCurrentLoggedIn();
            if (Optional.ofNullable(user).map(User::getId).isEmpty()) {
                throw new ImageException("Unauthorized", Response.Status.UNAUTHORIZED);
            }

            validateUploadedImage(imageFile);

            if (StringUtils.isBlank(altText)) {
                altText = imageFile.name();
            }

            Image image = new Image();
            image.setAltText(altText);
            image.setContent(Files.readAllBytes(imageFile.uploadedFile()));
            imageRepository.persist(image);
            return image;
        } catch (IOException e) {
            throw new ImageException("Failed to upload image", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateUploadedImage(FileUpload imageFile) {
        String mimeType = imageFile.contentType();
        if (mimeType == null || !mimeType.startsWith("image/")) {
            throw new ImageException("Uploaded file is not image", Response.Status.BAD_REQUEST);
        }
    }
}
