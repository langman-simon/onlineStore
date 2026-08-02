package com.hyperion.controller;

import com.hyperion.model.CustomerOrder;
import com.hyperion.model.User;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.service.OrderService;
import com.hyperion.service.UserService;
import com.hyperion.session.SessionCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final SessionCart sessionCart;
    private final CustomerOrderRepository customerOrderRepository;
    private final UserService userService;
    @Value("${paypal.sandbox-url}")
    private String paypalSandboxUrl;

    @Value("${paypal.seller-email}")
    private String paypalSellerEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    public OrderController(
            OrderService orderService,
            SessionCart sessionCart,
            CustomerOrderRepository customerOrderRepository,
            UserService userService) {
        this.orderService = orderService;
        this.sessionCart = sessionCart;
        this.customerOrderRepository = customerOrderRepository;
        this.userService = userService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (sessionCart.getCart().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", sessionCart.getCart());
        model.addAttribute("title", "Récapitulatif de commande");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/checkout.jsp"
        );

        return "template/template";
    }

    @PostMapping("/confirm")
    public String confirmOrder(
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            User user = userService.findByLogin(authentication.getName()).orElseThrow(()-> new IllegalArgumentException("Utilisateur introuvable."));
            CustomerOrder order =
                    orderService.validateOrder(sessionCart.getCart(), user, authentication.isAuthenticated());

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Commande confirmée."
            );

            return "redirect:/order/" + order.getId();

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );

            return "redirect:/checkout";
        }
    }

    @GetMapping("/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Authentication authentication,
            Model model
    ) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Commande n°" + id + " introuvable"
                        )
                );

        if (!order.getUser().getLogin()
                .equals(authentication.getName())) {
            throw new IllegalArgumentException(
                    "Accès interdit à cette commande."
            );
        }

        model.addAttribute("order", order);
        model.addAttribute("paypalSandboxUrl", paypalSandboxUrl);
        model.addAttribute("paypalSellerEmail", paypalSellerEmail);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("title", "Commande n°" + order.getId());
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/details.jsp"
        );

        return "template/template";
    }

    @GetMapping("/{id}/payment/success")
    public String paymentSuccess(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            orderService.validatePayment(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Paiement validé."
            );

            return "redirect:/order/" + id;

        } catch (IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    exception.getMessage()
            );

            return "redirect:/order/" + id;
        }
    }

    @GetMapping("/{id}/payment/cancel")
    public String paymentCancel(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute(
                "error",
                "Paiement annulé."
        );

        return "redirect:/order/" + id;
    }

    @GetMapping
    public String listOrders(
            Authentication authentication,
            Model model
    ) {
        String login = authentication.getName();

        List<CustomerOrder> orders =
                customerOrderRepository
                        .findByUserLoginOrderByCreatedAtDesc(login);

        model.addAttribute("orders", orders);
        model.addAttribute("title", "Mes commandes");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/list.jsp"
        );

        return "template/template";
    }

}