package com.hampcode.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class LoginController {

	@GetMapping(value= {"/login","/"})
	public String login(@RequestParam(value="error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Principal principal, Model model) {
		
		if(principal!=null) {
			model.addAttribute("username",principal.getName());
			return "redirect:/dashboard";			
		}
		
		if(error!=null) {
			model.addAttribute("error","Error de login, por favor vuelva intentarlo");
		}
		
		if(logout!=null) {
			model.addAttribute("success","Ha cerrado sesión con éxito!");
		}
		return "login";
	}
	
	
}
