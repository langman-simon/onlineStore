package com.hyperion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PayPalIpnService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PayPalIpnService.class);

    private final OrderService orderService;
    private final HttpClient httpClient;
    private final URI verificationUri;
    private final String sellerEmail;
    private final String currency;

    public PayPalIpnService(
            OrderService orderService,
            @Value("${paypal.ipn-verification-url}") String verificationUrl,
            @Value("${paypal.seller-email}") String sellerEmail,
            @Value("${paypal.currency}") String currency
    ) {
        this.orderService = orderService;
        this.verificationUri = URI.create(verificationUrl);
        this.sellerEmail = sellerEmail;
        this.currency = currency;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public void process(byte[] rawBody)
            throws IOException, InterruptedException {
        if (rawBody == null || rawBody.length == 0) {
            return;
        }

        String rawPayload = new String(
                rawBody,
                StandardCharsets.UTF_8
        );

        if (!isVerifiedByPayPal(rawPayload)) {
            LOGGER.warn("PayPal IPN notification rejected: INVALID");
            return;
        }

        Map<String, String> values = parseForm(rawPayload);

        if (!"Completed".equals(values.get("payment_status"))) {
            return;
        }

        if (!sellerEmail.equalsIgnoreCase(
                values.getOrDefault("receiver_email", "")
        )) {
            LOGGER.warn("PayPal IPN receiver does not match configured seller");
            return;
        }

        if (!currency.equalsIgnoreCase(
                values.getOrDefault("mc_currency", "")
        )) {
            LOGGER.warn("PayPal IPN currency does not match configured currency");
            return;
        }

        String reference = values.get("txn_id");
        String orderIdValue = values.get("custom");
        String paidAmountValue = values.get("mc_gross");

        if (reference == null || reference.isBlank()
                || orderIdValue == null || orderIdValue.isBlank()
                || paidAmountValue == null || paidAmountValue.isBlank()) {
            LOGGER.warn("PayPal IPN is missing required payment fields");
            return;
        }

        try {
            Long orderId = Long.valueOf(orderIdValue);
            BigDecimal paidAmount = new BigDecimal(paidAmountValue);

            orderService.validatePayment(
                    orderId,
                    paidAmount,
                    reference
            );

            LOGGER.info(
                    "PayPal payment validated for order {} with reference {}",
                    orderId,
                    reference
            );
        } catch (IllegalArgumentException exception) {
            LOGGER.warn(
                    "PayPal IPN could not validate an order: {}",
                    exception.getMessage()
            );
        }
    }

    private boolean isVerifiedByPayPal(String rawPayload)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(verificationUri)
                .timeout(Duration.ofSeconds(15))
                .header(
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                )
                .POST(HttpRequest.BodyPublishers.ofString(
                        "cmd=_notify-validate&" + rawPayload,
                        StandardCharsets.UTF_8
                ))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString(
                        StandardCharsets.UTF_8
                )
        );

        return response.statusCode() == 200
                && "VERIFIED".equals(response.body().trim());
    }

    private Map<String, String> parseForm(String rawPayload) {
        Map<String, String> values = new LinkedHashMap<>();

        for (String pair : rawPayload.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = decode(parts[0]);
            String value = parts.length == 2
                    ? decode(parts[1])
                    : "";

            values.put(key, value);
        }

        return values;
    }

    private String decode(String value) {
        return URLDecoder.decode(
                value,
                StandardCharsets.UTF_8
        );
    }
}
