package com.hyperion.controller;

import com.hyperion.model.CustomerOrder;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.service.OrderService;
import com.hyperion.service.PromotionService;
import com.hyperion.session.Panel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final Panel panel;
    private final CustomerOrderRepository customerOrderRepository;
    private final PromotionService promotionService;

    public OrderController(
            OrderService orderService,
            Panel panel,
            CustomerOrderRepository customerOrderRepository,
            PromotionService promotionService
    ) {
        this.orderService = orderService;
        this.panel = panel;
        this.customerOrderRepository = customerOrderRepository;
        this.promotionService = promotionService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        if (panel.getCart().isEmpty()) {
            return "redirect:/cart";
        }

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        BigDecimal originalPrice = panel.getCart().getTotalPrice();
        BigDecimal discount = promotionService.calculateDiscount(originalPrice, isAuthenticated);
        BigDecimal finalPrice = promotionService.calculateFinalPrice(originalPrice, isAuthenticated);

        model.addAttribute("originalPrice", originalPrice);
        model.addAttribute("discount", discount);
        model.addAttribute("finalPrice", finalPrice);

        model.addAttribute("cart", panel.getCart());
        model.addAttribute("title", "Récapitulatif de commande");
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/checkout.jsp"
        );

        return "template/template";
    }

    @PostMapping("/orders/confirm")
    public String confirmOrder(
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        try {
            CustomerOrder order =
                    orderService.validateOrder(panel.getCart(), isAuthenticated);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Commande confirmée."
            );

            return "redirect:/orders/" + order.getId();

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
            Model model
    ) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Commande n" + id + " introuvable"
                        )
                );

        model.addAttribute("order", order);
        model.addAttribute("title", "Commande n°" + order.getId());
        model.addAttribute(
                "body",
                "/WEB-INF/jsp/order/details.jsp"
        );

        return "template/template";
    }

}