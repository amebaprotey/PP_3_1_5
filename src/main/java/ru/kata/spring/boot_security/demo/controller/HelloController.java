package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;

@Controller
public class HelloController {
	private  final UserRepository userRepository;
	@Autowired
	public HelloController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		return "redirect:/user";
	}
	@GetMapping(value = "/index")
	public String getIndex(ModelMap model) {
		return "index";
	}
	@GetMapping(value = "/user")
	public String pageForAuthenticatedUsers(Principal principal, ModelMap model) {
		model.addAttribute("user", userRepository.findByUsername(principal.getName()).get());
		return "user";
	}

}