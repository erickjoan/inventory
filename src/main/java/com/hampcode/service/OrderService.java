package com.hampcode.service;

import java.util.List;
import java.util.Optional;

import com.hampcode.model.Order;

public interface OrderService extends CRUDService<Order> {
	Optional<Order> getOrderByIdWithCustomerWithOrderItemWithProduct(Long ordeId);

	List<Order> getOrderByCustomerId(Long customerId);

}
