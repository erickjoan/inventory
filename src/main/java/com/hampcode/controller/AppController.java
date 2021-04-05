package com.hampcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping("/dashboard")
	public String viewHomerPage() {
		return "index";
	}
}
