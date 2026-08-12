package com.hyperion.controller;

import com.hyperion.service.PayPalIpnService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class PaymentController {

    private final PayPalIpnService payPalIpnService;

    public PaymentController(PayPalIpnService payPalIpnService) {
        this.payPalIpnService = payPalIpnService;
    }

    @PostMapping(
            value = "/payment/paypal/ipn",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseBody
    public ResponseEntity<Void> paypalIpn(
            HttpServletRequest request
    ) {
        try {
            payPalIpnService.process(
                    request.getInputStream().readAllBytes()
            );

            return ResponseEntity.ok().build();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return ResponseEntity.internalServerError().build();
        } catch (IOException exception) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
