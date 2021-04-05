package com.hampcode.service;

import java.util.List;

import com.hampcode.model.Product;

public interface ProductService extends CRUDService<Product>{
	List<Product> getByNameContaint(String term);
}
