package com.hampcode.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hampcode.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	//@Query("SELECT p FROM categories p WHERE p.name LIKE %?1%")
	List<Category> findByName(String name);
}
