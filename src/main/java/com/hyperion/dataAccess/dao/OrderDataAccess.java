package com.hyperion.dataAccess.dao;

import com.hyperion.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDataAccess {

    Order create(Order order, Long userId);

    Optional<Order> findById(Long id);

    Optional<Order> findByIdAndUserLogin(Long id, String login);

    List<Order> findByUserLogin(String login);

    boolean existsByPaymentReference(String paymentReference);

    void markAsPaid(Long id, String paymentReference);

    void delete(Long id);
}
