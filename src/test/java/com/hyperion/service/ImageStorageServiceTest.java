package com.hyperion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageStorageServiceTest {

    @TempDir
    Path tempDir;

    private ImageStorageService imageStorageService;

    @BeforeEach
    void setUp() {
        imageStorageService = new ImageStorageService(tempDir);
    }

    @Test
    void shouldRejectNullImage() {
        assertThatThrownBy(() ->
                imageStorageService.saveProductImage(null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.image.required");
    }

    @Test
    void shouldRejectEmptyImage() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "product.png",
                "image/png",
                new byte[0]
        );

        assertThatThrownBy(() ->
                imageStorageService.saveProductImage(image)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.image.required");
    }

    @Test
    void shouldRejectUnsupportedImageFormat() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "product.gif",
                "image/gif",
                new byte[]{1, 2, 3}
        );

        assertThatThrownBy(() ->
                imageStorageService.saveProductImage(image)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.image.format");
    }

    @Test
    void shouldSaveValidImage() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "product.PNG",
                "image/png",
                new byte[]{1, 2, 3}
        );

        String path = imageStorageService.saveProductImage(image);

        assertThat(path)
                .startsWith("/uploads/weapons/")
                .endsWith(".png");

        String storedFilename = path.substring("/uploads/weapons/".length());
        assertThat(tempDir.resolve(storedFilename)).exists();
    }
}
