package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hampcode.model.Product;
import com.hampcode.repository.ProductRepository;
import com.hampcode.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAll() throws Exception {
		return productRepository.findAll();
	}

	@Override
	public Page<Product> getAll(Pageable pageable) throws Exception {
		return productRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Product saveOrUpdate(Product entity) throws Exception {
		return productRepository.save(entity);
	}

	@Override
	public Optional<Product> getOne(Long id) throws Exception {
		return productRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		productRepository.deleteById(id);

	}

	@Override
	public List<Product> getByNameContaint(String term) {
		return productRepository.findByNameContaint(term);
	}

}
