package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository {

    Optional<Orders> findById(long id);

    List<Orders> findAll(Long offset, Long size);

    Boolean accept(Long id);

    Boolean reject(Long id, String message);

    Boolean shipping(Long id);

    Boolean complete(Long id);

    ReviewDto review(Long id, String content);
}