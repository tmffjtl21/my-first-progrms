package com.github.prgrms.orders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Orders> findById(Long orderId) {
        checkNotNull(orderId, "orderId must be provided");

        return ordersRepository.findById(orderId);
    }

    @Transactional(readOnly = true)
    public List<Orders> findAll(Long offset, Long size) {
        return ordersRepository.findAll(offset, size);
    }

    @Transactional
    public Boolean accept(Long id) {

        return ordersRepository.accept(id);
    }

    @Transactional
    public Boolean reject(Long id, String message) {

        return ordersRepository.reject(id, message);
    }

    @Transactional
    public Boolean shipping(Long id) {

        return ordersRepository.shipping(id);
    }

    public Boolean complete(Long id) {

        return ordersRepository.complete(id);
    }

    public ReviewDto review(Long id, String content) {

        return ordersRepository.review(id, content);
    }
}