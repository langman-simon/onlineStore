package com.hyperion.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Service
public class GlobalBannerService {

    private final MessageSource messageSource;

    public GlobalBannerService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void success(
            HttpSession session,
            String messageCode,
            Object... arguments
    ) {
        setMessage(
                session,
                messageCode,
                "success",
                arguments
        );
    }

    public void error(
            HttpSession session,
            String messageCode,
            Object... arguments
    ) {
        setMessage(
                session,
                messageCode,
                "error",
                arguments
        );
    }

    public void warning(
            HttpSession session,
            String messageCode,
            Object... arguments
    ) {
        setMessage(
                session,
                messageCode,
                "warning",
                arguments
        );
    }

    public void info(
            HttpSession session,
            String messageCode,
            Object... arguments
    ) {
        setMessage(
                session,
                messageCode,
                "info",
                arguments
        );
    }

    private Locale resolveLocale(HttpSession session) {
        Object sessionLocale = session.getAttribute(
                SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME
        );

        if (sessionLocale instanceof Locale locale) {
            return locale;
        }

        return Locale.FRENCH;
    }

    private void setMessage(
            HttpSession session,
            String messageCode,
            String type,
            Object... arguments
    ) {
        Locale locale = resolveLocale(session);

        String message = messageSource.getMessage(
                messageCode,
                arguments,
                messageCode,
                locale
        );

        session.setAttribute(
                "globalBannerMessage",
                message
        );

        session.setAttribute(
                "globalBannerType",
                type
        );
    }
}
