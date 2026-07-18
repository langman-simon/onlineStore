package com.hyperion.session;

import com.hyperion.cart.Cart;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Panel {

    private final Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

}