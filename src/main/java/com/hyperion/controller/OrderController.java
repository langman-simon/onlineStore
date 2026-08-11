package com.hyperion.controller;

import com.hyperion.cart.Cart;
import com.hyperion.model.CustomerOrder;
import com.hyperion.model.User;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.service.GlobalBannerService;
import com.hyperion.service.OrderService;
import com.hyperion.service.PromotionService;
import com.hyperion.service.UserService;
import com.hyperion.session.SessionCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final SessionCart sessionCart;
    private final CustomerOrderRepository customerOrderRepository;
    private final UserService userService;
    private final PromotionService promotionService;
    private final GlobalBannerService globalBannerService;

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
            UserService userService,
            PromotionService promotionService,
            GlobalBannerService globalBannerService
    ) {
        this.orderService = orderService;
        this.sessionCart = sessionCart;
        this.customerOrderRepository = customerOrderRepository;
        this.userService = userService;
        this.promotionService = promotionService;
        this.globalBannerService = globalBannerService;
    }

    @GetMapping("/checkout")
    public String checkout(
            Authentication authentication,
            Model model,
            HttpSession session
    ) {
        Cart cart = sessionCart.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            globalBannerService.warning(
                    session,
                    "error.cart.empty"
            );

            return "redirect:/cart";
        }

        boolean authenticated =
                isAuthenticated(authentication);

        BigDecimal originalPrice =
                cart.getTotalPrice();

        BigDecimal discountAmount =
                promotionService.calculateDiscount(
                        originalPrice,
                        authenticated
                );

        BigDecimal finalPrice =
                promotionService.calculateFinalPrice(
                        originalPrice,
                        authenticated
                );

        BigDecimal deliveryFee =
                promotionService.calculateDeliveryFee(
                        originalPrice,
                        authenticated
                );

        boolean freeDelivery =
                promotionService.isFreeDeliveryApplied(
                        originalPrice,
                        authenticated
                );

        model.addAttribute("cart", cart);
        model.addAttribute("originalPrice", originalPrice);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("deliveryFee", deliveryFee);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("freeDelivery", freeDelivery);

        model.addAttribute(
                "titleKey",
                "page.checkout"
        );
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/checkout.jsp"
        );

        return "template/template";
    }

    @PostMapping("/confirm")
    public String confirmOrder(
            Authentication authentication,
            HttpSession session
    ) {
        try {
            User user = userService
                    .findByLogin(authentication.getName())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "error.user.notFound"
                            )
                    );

            CustomerOrder order =
                    orderService.validateOrder(
                            sessionCart.getCart(),
                            user,
                            isAuthenticated(authentication)
                    );

            globalBannerService.success(
                    session,
                    "message.order.confirmed"
            );

            return "redirect:/order/" + order.getId();

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );

            return "redirect:/order/checkout";
        }
    }

    @GetMapping("/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Authentication authentication,
            Model model,
            HttpSession session
    ) {
        Optional<CustomerOrder> orderOpt =
                findOrderForCurrentUser(
                        id,
                        authentication
                );

        if (orderOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.order.notFound"
            );

            return "redirect:/order";
        }

        CustomerOrder order = orderOpt.get();

        model.addAttribute("order", order);
        model.addAttribute(
                "paypalSandboxUrl",
                paypalSandboxUrl
        );
        model.addAttribute(
                "paypalSellerEmail",
                paypalSellerEmail
        );
        model.addAttribute("baseUrl", baseUrl);

        model.addAttribute(
                "titleKey",
                "page.orderDetails"
        );
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/details.jsp"
        );

        return "template/template";
    }

    @GetMapping("/{id}/payment/success")
    public String paymentSuccess(
            @PathVariable Long id,
            Authentication authentication,
            HttpSession session
    ) {
        Optional<CustomerOrder> orderOpt =
                findOrderForCurrentUser(
                        id,
                        authentication
                );

        if (orderOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.order.notFound"
            );

            return "redirect:/order";
        }

        try {
            orderService.validatePayment(id);

            globalBannerService.success(
                    session,
                    "message.order.paymentValidated"
            );

        } catch (IllegalArgumentException exception) {
            globalBannerService.error(
                    session,
                    exception.getMessage()
            );
        }

        return "redirect:/order/" + id;
    }

    @GetMapping("/{id}/payment/cancel")
    public String paymentCancel(
            @PathVariable Long id,
            Authentication authentication,
            HttpSession session
    ) {
        Optional<CustomerOrder> orderOpt =
                findOrderForCurrentUser(
                        id,
                        authentication
                );

        if (orderOpt.isEmpty()) {
            globalBannerService.error(
                    session,
                    "error.order.notFound"
            );

            return "redirect:/order";
        }

        globalBannerService.warning(
                session,
                "message.order.paymentCancelled"
        );

        return "redirect:/order/" + id;
    }

    @GetMapping
    public String listOrders(
            Authentication authentication,
            Model model
    ) {
        List<CustomerOrder> orders =
                customerOrderRepository
                        .findByUserLoginOrderByCreatedAtDesc(
                                authentication.getName()
                        );

        model.addAttribute("orders", orders);
        model.addAttribute("titleKey", "page.orders");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/list.jsp"
        );

        return "template/template";
    }

    private Optional<CustomerOrder> findOrderForCurrentUser(
            Long id,
            Authentication authentication
    ) {
        Optional<CustomerOrder> orderOpt =
                customerOrderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return Optional.empty();
        }

        CustomerOrder order = orderOpt.get();

        if (!order.getUser()
                .getLogin()
                .equals(authentication.getName())) {

            throw new AccessDeniedException(
                    "error.accessDenied"
            );
        }

        return orderOpt;
    }

    private boolean isAuthenticated(
            Authentication authentication
    ) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication
                instanceof AnonymousAuthenticationToken);
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(
            @PathVariable Long id,
            Authentication authentication,
            HttpSession session
    ) {
        try {
            orderService.cancelOrder(
                    id,
                    authentication.getName()
            );

            globalBannerService.success(
                    session,
                    "message.order.cancelled"
            );

        } catch (IllegalArgumentException exception) {

            globalBannerService.error(
                    session,
                    exception.getMessage()
            );
        }

        return "redirect:/order";
    }

}
