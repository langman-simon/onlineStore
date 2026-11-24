package com.hyperion.dataAccess.dao;

import com.hyperion.dataAccess.entity.CustomerOrderEntity;
import com.hyperion.dataAccess.entity.OrderItemEntity;
import com.hyperion.dataAccess.repository.CustomerOrderRepository;
import com.hyperion.dataAccess.repository.ProductRepository;
import com.hyperion.dataAccess.repository.UserRepository;
import com.hyperion.dataAccess.util.OrderMapper;
import com.hyperion.model.Order;
import com.hyperion.model.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderDAO implements OrderDataAccess {

    private final CustomerOrderRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    public OrderDAO(
            CustomerOrderRepository repository,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderMapper mapper
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Order create(Order order, Long userId) {
        CustomerOrderEntity entity = new CustomerOrderEntity(
                order.getOriginalPrice(),
                order.getDiscountAmount(),
                order.getTotalPrice()
        );
        entity.setUser(userRepository.getReferenceById(userId));

        for (OrderItem item : order.getItems()) {
            entity.addItem(
                    new OrderItemEntity(
                            productRepository.getReferenceById(item.getProduct().getId()),
                            item.getQuantity()
                    )
            );
        }

        return mapper.toModel(repository.save(entity));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<Order> findByIdAndUserLogin(Long id, String login) {
        return repository.findByIdAndUserLogin(id, login).map(mapper::toModel);
    }

    @Override
    public List<Order> findByUserLogin(String login) {
        return mapper.toModels(repository.findByUserLoginOrderByCreatedAtDesc(login));
    }

    @Override
    public boolean existsByPaymentReference(String paymentReference) {
        return repository.existsByPaymentReference(paymentReference);
    }

    @Override
    @Transactional
    public void markAsPaid(Long id, String paymentReference) {
        CustomerOrderEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error.order.notFound"));

        entity.setPaymentReference(paymentReference);
        entity.setStatus("PAID");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
