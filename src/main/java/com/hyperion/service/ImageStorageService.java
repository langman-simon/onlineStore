package com.hyperion.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageStorageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final Path uploadDirectory =
            Path.of("uploads", "weapons")
                    .toAbsolutePath()
                    .normalize();

    public ImageStorageService() {
        try {
            Files.createDirectories(uploadDirectory);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "error.image.folder",
                    exception
            );
        }
    }

    public String saveWeaponImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException(
                    "error.image.required"
            );
        }

        if (!ALLOWED_CONTENT_TYPES.contains(image.getContentType())) {
            throw new IllegalArgumentException(
                    "error.image.format"
            );
        }

        String extension = getExtension(image.getOriginalFilename());

        String storedFilename =
                UUID.randomUUID() + extension;

        Path destination = uploadDirectory
                .resolve(storedFilename)
                .normalize();

        if (!destination.startsWith(uploadDirectory)) {
            throw new IllegalArgumentException(
                    "error.image.path"
            );
        }

        try {
            Files.copy(
                    image.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "error.image.save",
                    exception
            );
        }

        return "/uploads/weapons/" + storedFilename;
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }

        int dotIndex = originalFilename.lastIndexOf('.');

        if (dotIndex < 0) {
            return "";
        }

        return originalFilename
                .substring(dotIndex)
                .toLowerCase();
    }
}