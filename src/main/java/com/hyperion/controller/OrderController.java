package com.hyperion.controller;

import com.hyperion.model.CustomerOrder;
import com.hyperion.repository.CustomerOrderRepository;
import com.hyperion.service.OrderService;
import com.hyperion.session.SessionCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final SessionCart sessionCart;
    private final CustomerOrderRepository customerOrderRepository;

    public OrderController(
            OrderService orderService,
            SessionCart sessionCart,
            CustomerOrderRepository customerOrderRepository
    ) {
        this.orderService = orderService;
        this.sessionCart = sessionCart;
        this.customerOrderRepository = customerOrderRepository;
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
            RedirectAttributes redirectAttributes
    ) {
        try {
            CustomerOrder order =
                    orderService.validateOrder(sessionCart.getCart());

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