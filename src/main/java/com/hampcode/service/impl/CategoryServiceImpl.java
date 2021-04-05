package com.hampcode.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.hampcode.model.Category;
import com.hampcode.repository.CategoryRepository;
import com.hampcode.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> getAll() throws Exception {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Page<Category> getAll(Pageable pageable) throws Exception {
		return categoryRepository.findAll(pageable);
	}
	
	@Transactional
	@Override
	public Category saveOrUpdate(Category entity) throws Exception {
		return categoryRepository.save(entity);
	}

	@Override
	public Optional<Category> getOne(Long id) throws Exception {
		return categoryRepository.findById(id);
	}
	
	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		 categoryRepository.deleteById(id);
		
	}

	@Override
	public List<Category> findByNameCategoria(String name) {
		return categoryRepository.findByName(name);
	}


	

}
