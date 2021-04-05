package com.hampcode.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hampcode.common.pagination.PageRender;
import com.hampcode.model.Account;
import com.hampcode.model.Product;
import com.hampcode.repository.RoleRepository;
import com.hampcode.repository.UserRepository;
import com.hampcode.service.RoleService;
import com.hampcode.service.UserService;
import com.hampcode.util.Contants;

@Controller
@RequestMapping("/users")
public class UserController {

	//@Autowired
	//private UserRepository userRepository;
	
	//@Autowired
	//private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoderr;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoderr() {
		return new BCryptPasswordEncoder();
	}
	/*
	@GetMapping
	public String listUsers(Model model) {
		return listByPage(model,1, "id","asc","");
	}
	
	@GetMapping("/page/{pageNumber}")
	public String listByPage(Model model, @PathVariable int pageNumber,
			@RequestParam String sortField,@RequestParam String sortDir,
			@RequestParam String keyword) {		
		
		Sort sort=Sort.by(sortField);
		sort=sortDir.equals("asc")?sort.ascending():sort.descending();		
		Page<Account> page=userRepository.findAll(keyword, PageRequest.of(pageNumber-1,Contants.PAGE_SIZE,sort));		
		model.addAttribute("currentPage",pageNumber);
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("totalPages",page.getTotalPages());		
		model.addAttribute("users",page.getContent());
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",sortDir.equals("asc")?"desc":"asc");
		model.addAttribute("keyword",keyword);		
		return "users";
	}
	*/
	@GetMapping("/new")
	public String showCreateNewUserForm(Model model) throws Exception {
		model.addAttribute("user", new Account());
		model.addAttribute("roles",roleService.getAll());
		
		return "user_form";
	}
	
	@GetMapping(value ="/lista")
	public String getAllUser(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

		try {
			Pageable pageRequest = PageRequest.of(page, 5);

			Page<Account> users = userService.getAll(pageRequest);

			PageRender<Account> pageRender = new PageRender<Account>("/users/lista/", users);

			model.addAttribute("title", "Users");

			model.addAttribute("users", users);
			model.addAttribute("page", pageRender);

		} catch (Exception e) {

		}

		return "users";
	}
	
	@Transactional
	@PostMapping("/save")
	public String saveUser(Account account,Model model) throws Exception {
		String pass=passwordEncoderr.encode(account.getPassword()); 
		account.setPassword(pass);
		userService.saveOrUpdate(account);
		return "redirect:/users/lista";
	}
	
	
	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable Long id,Model model) throws Exception {
		model.addAttribute("user", userService.getOne(id).get());	
		model.addAttribute("roles",roleService.getAll());
			
		return "user_form";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) throws Exception {
		userService.deleteById(id);		
		return "redirect:/users/lista";
	}
}


