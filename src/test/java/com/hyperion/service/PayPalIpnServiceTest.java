package com.hyperion.service;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class PayPalIpnServiceTest {

    private OrderService orderService;
    private PayPalIpnService payPalIpnService;
    private HttpServer server;
    private String paypalResponse;

    @BeforeEach
    void setUp() throws IOException {
        orderService = mock(OrderService.class);
        paypalResponse = "VERIFIED";

        server = HttpServer.create(new InetSocketAddress(0), 0);

        server.createContext("/paypal", exchange -> {
            byte[] response =
                    paypalResponse.getBytes(StandardCharsets.UTF_8);

            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });

        server.start();

        payPalIpnService = new PayPalIpnService(
                orderService,
                "http://localhost:"
                        + server.getAddress().getPort()
                        + "/paypal",
                "seller@example.com",
                "EUR"
        );
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @Test
    void shouldIgnoreNullBody() throws Exception {
        payPalIpnService.process(null);

        verifyNoInteractions(orderService);
    }

    @Test
    void shouldIgnoreEmptyBody() throws Exception {
        payPalIpnService.process(new byte[0]);

        verifyNoInteractions(orderService);
    }

    @Test
    void shouldIgnoreInvalidPaypalVerification() throws Exception {
        paypalResponse = "INVALID";

        payPalIpnService.process(validPayload());

        verifyNoInteractions(orderService);
    }

    @Test
    void shouldIgnorePaymentWithWrongSellerEmail() throws Exception {
        String payload = validPayloadString()
                .replace(
                        "seller%40example.com",
                        "other%40example.com"
                );

        payPalIpnService.process(
                payload.getBytes(StandardCharsets.UTF_8)
        );

        verifyNoInteractions(orderService);
    }

    @Test
    void shouldIgnorePaymentWithWrongCurrency() throws Exception {
        String payload = validPayloadString()
                .replace(
                        "mc_currency=EUR",
                        "mc_currency=USD"
                );

        payPalIpnService.process(
                payload.getBytes(StandardCharsets.UTF_8)
        );

        verifyNoInteractions(orderService);
    }

    @Test
    void shouldIgnorePaymentThatIsNotCompleted() throws Exception {
        String payload = validPayloadString()
                .replace(
                        "payment_status=Completed",
                        "payment_status=Pending"
                );

        payPalIpnService.process(
                payload.getBytes(StandardCharsets.UTF_8)
        );

        verifyNoInteractions(orderService);
    }

    @Test
    void shouldValidateCompletedPayment() throws Exception {
        payPalIpnService.process(validPayload());

        verify(orderService).validatePayment(
                42L,
                new BigDecimal("100.00"),
                "PAYPAL-123"
        );
    }

    private byte[] validPayload() {
        return validPayloadString()
                .getBytes(StandardCharsets.UTF_8);
    }

    private String validPayloadString() {
        return "payment_status=Completed"
                + "&receiver_email=seller%40example.com"
                + "&mc_currency=EUR"
                + "&txn_id=PAYPAL-123"
                + "&custom=42"
                + "&mc_gross=100.00";
    }
}
