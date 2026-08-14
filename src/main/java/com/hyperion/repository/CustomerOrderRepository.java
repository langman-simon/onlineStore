package com.hyperion.repository;

import com.hyperion.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByUserLoginOrderByCreatedAtDesc(
            String login
    );

    Optional<CustomerOrder> findByIdAndUserLogin(
            Long id,
            String login
    );

    boolean existsByPaymentReference(
            String paymentReference
    );
}
