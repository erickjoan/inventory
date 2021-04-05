package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.model.Customer;
import com.hampcode.repository.CustomerRepository;
import com.hampcode.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getAll() throws Exception {
		return (List<Customer>) customerRepository.findAll();
	}

	@Override
	public Page<Customer> getAll(Pageable pageable) throws Exception {
		return customerRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Customer saveOrUpdate(Customer entity) throws Exception {
		return customerRepository.save(entity);
	}

	@Override
	public Optional<Customer> getOne(Long id) throws Exception {
		return customerRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		customerRepository.deleteById(id);
		
	}

}
