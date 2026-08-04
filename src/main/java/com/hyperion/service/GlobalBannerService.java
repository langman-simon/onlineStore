package com.hyperion.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class GlobalBannerService {

    public void success(
            HttpSession session,
            String message
    ) {
        setMessage(session, message, "success");
    }

    public void error(
            HttpSession session,
            String message
    ) {
        setMessage(session, message, "error");
    }

    public void warning(
            HttpSession session,
            String message
    ) {
        setMessage(session, message, "warning");
    }

    public void info(
            HttpSession session,
            String message
    ) {
        setMessage(session, message, "info");
    }

    private void setMessage(
            HttpSession session,
            String message,
            String type
    ) {
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