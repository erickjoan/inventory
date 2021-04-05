package com.hampcode.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hampcode.common.pagination.PageRender;
import com.hampcode.model.Category;
import com.hampcode.model.Product;
import com.hampcode.repository.CategoryRepository;
import com.hampcode.repository.ProductRepository;
import com.hampcode.service.CategoryService;
import com.hampcode.service.ProductService;
import com.hampcode.util.Contants;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository; 
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	/*
	
	@GetMapping
	public String listProducts(Model model) {
		return listByPage(model,1, "id","asc","");
	}

	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber,
			@RequestParam String sortField,@RequestParam String sortDir,
			@RequestParam String keyword) {		
		
		Sort sort=Sort.by(sortField);
		sort=sortDir.equals("asc")?sort.ascending():sort.descending();		
		Page<Product> page=productRepository.findAll(keyword, PageRequest.of(pageNumber-1,Contants.PAGE_SIZE,sort));		
		model.addAttribute("currentPage",pageNumber);
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("totalPages",page.getTotalPages());		
		model.addAttribute("products",page.getContent());
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
		model.addAttribute("keyword",keyword);		
		return "products";
	}
	*/
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/new")
	public String showProductNew(Model model) throws Exception {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.getAll());
		return "product/product_form";
	}
	
	@GetMapping(value ="/lista")
	public String getAllCategory(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		try {
			Pageable pageRequest = PageRequest.of(page, 5);

			Page<Product> products = productService.getAll(pageRequest);

			PageRender<Product> pageRender = new PageRender<Product>("/products/lista/", products);

			model.addAttribute("title", "Products");

			model.addAttribute("products", products);
			model.addAttribute("page", pageRender);

		} catch (Exception e) {

		}

		return "product/products";
	}
	
	@Transactional
	@PostMapping("/save")
	public String saveProduct(Product product, HttpServletRequest request) throws Exception {
		String[] detaildIds=request.getParameterValues("detailId");
		String[] detaildNames=request.getParameterValues("detailName");
		String[] detaildValues=request.getParameterValues("detailValue");
				
		for (int i = 0; i < detaildNames.length; i++) {
			if(detaildIds!=null && detaildIds.length>0) {
				product.addDetail(Long.valueOf(detaildIds[i]),detaildNames[i],detaildValues[i]);
			}else {
				product.addDetail(detaildNames[i],detaildValues[i]);
			}
		}
		
		productService.saveOrUpdate(product);
		return "redirect:/products/lista";
	}
	
	
	@GetMapping("/edit/{id}")
	public String showProductEditForm(@PathVariable Long id,Model model) throws Exception {
		model.addAttribute("product", productService.getOne(id).get());
		model.addAttribute("categories", categoryService.getAll());
		return "product/product_form";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) throws Exception {
		productService.deleteById(id);		
		return "redirect:/products/lista";
	}
	
	@GetMapping(value = "/loadproducts/{term}", produces = { "application/json" })
	public @ResponseBody List<Product> loadProdutcs(@PathVariable String term) {
		List<Product> products=new ArrayList<>();
		try {
			products=productService.getByNameContaint(term);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
}


