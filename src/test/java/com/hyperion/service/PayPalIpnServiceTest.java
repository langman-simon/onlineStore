package com.hyperion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class PayPalIpnServiceTest {

    private OrderService orderService;
    private PayPalIpnService payPalIpnService;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        payPalIpnService = new PayPalIpnService(
                orderService,
                "https://example.com/paypal",
                "seller@example.com",
                "EUR"
        );
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
}
