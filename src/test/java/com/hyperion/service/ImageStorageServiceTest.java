package com.hyperion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageStorageServiceTest {

    private ImageStorageService imageStorageService;

    @BeforeEach
    void setUp() {
        imageStorageService = new ImageStorageService();
    }

    @Test
    void shouldRejectNullImage() {
        assertThatThrownBy(() ->
                imageStorageService.saveWeaponImage(null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.image.required");
    }

    @Test
    void shouldRejectEmptyImage() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "weapon.png",
                "image/png",
                new byte[0]
        );

        assertThatThrownBy(() ->
                imageStorageService.saveWeaponImage(image)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.image.required");
    }

    @Test
    void shouldRejectUnsupportedImageFormat() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "weapon.gif",
                "image/gif",
                new byte[]{1, 2, 3}
        );

        assertThatThrownBy(() ->
                imageStorageService.saveWeaponImage(image)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("error.image.format");
    }

    @Test
    void shouldSaveValidImage() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "weapon.PNG",
                "image/png",
                new byte[]{1, 2, 3}
        );

        String path = imageStorageService.saveWeaponImage(image);

        assertThat(path)
                .startsWith("/uploads/weapons/")
                .endsWith(".png");
    }
}
