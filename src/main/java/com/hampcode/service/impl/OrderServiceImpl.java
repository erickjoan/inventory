package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.model.Order;
import com.hampcode.repository.OrderRepository;
import com.hampcode.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<Order> getAll() throws Exception {
		return orderRepository.findAll();
	}

	@Override
	public Page<Order> getAll(Pageable pageable) throws Exception {
		return orderRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Order saveOrUpdate(Order entity) throws Exception {
		entity.getItems().forEach(item->item.setOrder(entity));
		return orderRepository.save(entity);
	}

	@Override
	public Optional<Order> getOne(Long id) throws Exception {
		return orderRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		orderRepository.deleteById(id);
		
	}

	@Override
	public Optional<Order> getOrderByIdWithCustomerWithOrderItemWithProduct(Long ordeId) {
		return orderRepository.findOrderByIdWithCustomerWithOrderItemWithProduct(ordeId);
	}

	@Override
	public List<Order> getOrderByCustomerId(Long customerId) {
		return orderRepository.findOrderByCustomerId(customerId);
	}

}
