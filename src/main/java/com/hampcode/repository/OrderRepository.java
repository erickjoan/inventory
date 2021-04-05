package com.hampcode.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hampcode.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("SELECT o FROM Order o JOIN FETCH o.customer c JOIN FETCH o.items i JOIN FETCH i.product WHERE o.id=?1")
	Optional<Order> findOrderByIdWithCustomerWithOrderItemWithProduct(Long ordeId);
	
	@Query(value="SELECT * FROM orders o WHERE o.customer_id=?1", nativeQuery = true)
	List<Order> findOrderByCustomerId(Long customerId);
	
}
