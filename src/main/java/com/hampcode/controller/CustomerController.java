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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hampcode.common.pagination.PageRender;
import com.hampcode.model.Customer;
import com.hampcode.service.CustomerService;

@Controller
@SessionAttributes("customer")
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(value ="/lista")
	public String getAllCustomer(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		try {
			Pageable pageRequest = PageRequest.of(page, 5);
			
			Page<Customer> customers=customerService.getAll(pageRequest);
			PageRender<Customer> pageRender = new PageRender<Customer>("/customers/lista/", customers);
			
			model.addAttribute("title", "	Customer");
			model.addAttribute("customers", customers);
			model.addAttribute("page", pageRender);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "customer/customer";
	}

	
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/new")
	public String NewCustomer(Model model) {
		Customer customer= new Customer();
		model.addAttribute("customer",customer);
		model.addAttribute("title","Customer");
		return "customer/new_customer";
		
	}
	
	@Transactional
	@PostMapping("/save")
	public String saveCategory(Customer customer,Model model) throws Exception {
		customerService.saveOrUpdate(customer);
		model.addAttribute("success","customer generada");
		return "redirect:/customers/lista";
	}
	
	@GetMapping("/edit/{id}")
	public String showProductEditForm(@PathVariable Long id,Model model) throws Exception {
		model.addAttribute("customer",customerService.getOne(id).get());;
		return "customer/new_customer";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id) throws Exception {
		customerService.deleteById(id);
		return "redirect:/customers/lista";
		}
}
