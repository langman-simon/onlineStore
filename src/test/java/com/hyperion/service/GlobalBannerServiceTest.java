package com.hyperion.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GlobalBannerServiceTest {

    private MessageSource messageSource;
    private HttpSession session;
    private GlobalBannerService bannerService;

    @BeforeEach
    void setUp() {
        messageSource = mock(MessageSource.class);
        session = mock(HttpSession.class);
        bannerService = new GlobalBannerService(messageSource);
    }

    @Test
    void shouldStoreSuccessMessageInSession() {
        when(messageSource.getMessage(
                "message.saved",
                new Object[0],
                Locale.FRENCH
        )).thenReturn("Saved");

        bannerService.success(session, "message.saved");

        verify(session).setAttribute(
                "globalBannerMessage",
                "Saved"
        );
        verify(session).setAttribute(
                "globalBannerType",
                "success"
        );
    }

    @Test
    void shouldStoreErrorMessageInSession() {
        when(messageSource.getMessage(
                "message.error",
                new Object[0],
                Locale.FRENCH
        )).thenReturn("Error");

        bannerService.error(session, "message.error");

        verify(session).setAttribute(
                "globalBannerMessage",
                "Error"
        );
        verify(session).setAttribute(
                "globalBannerType",
                "error"
        );
    }

    @Test
    void shouldUseLocaleStoredInSession() {
        Locale locale = Locale.ENGLISH;
        when(session.getAttribute(
                SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME
        )).thenReturn(locale);
        when(messageSource.getMessage(
                "message.info",
                new Object[0],
                locale
        )).thenReturn("Information");

        bannerService.info(session, "message.info");

        verify(messageSource).getMessage(
                "message.info",
                new Object[0],
                locale
        );
        verify(session).setAttribute(
                "globalBannerType",
                "info"
        );
    }
}
