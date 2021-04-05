package com.hampcode.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hampcode.common.pagination.PageRender;
import com.hampcode.model.Category;
import com.hampcode.service.CategoryService;

@Controller
@SessionAttributes("category")
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value ="/lista")
	public String getAllCategory(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		try {
			Pageable pageRequest = PageRequest.of(page, 5);

			Page<Category> categories = categoryService.getAll(pageRequest);

			PageRender<Category> pageRender = new PageRender<Category>("/categories/lista/", categories);

			model.addAttribute("title", "Category");

			model.addAttribute("categories", categories);
			model.addAttribute("page", pageRender);

		} catch (Exception e) {

		}

		return "category/category";
	}
	@GetMapping(value ="/listabuscar")
	public  String buscarPorCategory(@RequestParam String name,Model model,@ModelAttribute("category") Category category) {
		model.addAttribute("buscarCategoria",categoryService.findByNameCategoria(name));
		return null;
		
	}
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/new")
	public String NewCategory(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		model.addAttribute("title", "Category");
		return "category/new_category";
		
	}
	
	@Transactional
	@PostMapping("/save")
	public String saveCategory(Category category,Model model) throws Exception {
		
			categoryService.saveOrUpdate(category);
			model.addAttribute("success", "category Generada");
		
		return "redirect:/categories/lista";
	}
	
	@GetMapping("/edit/{id}")
	public String showProductEditForm(@PathVariable Long id,Model model) throws Exception {
		model.addAttribute("category", categoryService.getOne(id).get());;
		return "category/new_category";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id) throws Exception {
		categoryService.deleteById(id);		
		return "redirect:/categories/lista";
	}
}

