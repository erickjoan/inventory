package com.hampcode.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hampcode.model.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
	
}
