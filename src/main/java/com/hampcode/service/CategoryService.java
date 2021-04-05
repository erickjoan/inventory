package com.hampcode.service;

import java.util.List;

import com.hampcode.model.Category;

public interface CategoryService extends CRUDService<Category>{

	List<Category> findByNameCategoria(String name);
}
