package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		return "redirect:/login";
	}

	@RequestMapping(value = "/login")
	public String getLogin(@RequestParam(value = "error",required = false)String error,
						   @RequestParam(value = "logout",required = false)String logout,
						   Model model) {
		model.addAttribute("error", error != null);
		model.addAttribute("logout", logout != null);
		return "login";
	}
	@GetMapping("/user")
	public String getUsers(Principal principal, ModelMap model) {
		model.addAttribute("thisUser", userRepository.findByUsername(principal.getName()).get());
		return "user";
	}

}