package com.hyperion.dataAccess.repository;

import com.hyperion.dataAccess.entity.CustomerOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrderEntity, Long> {

    List<CustomerOrderEntity> findByUserLoginOrderByCreatedAtDesc(
            String login
    );

    Optional<CustomerOrderEntity> findByIdAndUserLogin(
            Long id,
            String login
    );

    boolean existsByPaymentReference(
            String paymentReference
    );
}
