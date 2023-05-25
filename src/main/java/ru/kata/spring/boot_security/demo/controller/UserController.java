package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servise.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUsers(ModelMap model){
        model.addAttribute("users",userService.findAll());
        return "users";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.findOne(id));
        return "show";
    }
    @GetMapping("/new")
    public String newUser(ModelMap model){
        model.addAttribute("user",new User());
        return "new";
    }
    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(value ="roles", required = false) List<Integer> idRoles){
        if (bindingResult.hasErrors())
            return "new";
        if(idRoles != null)
            user.setRoles(idRoles.stream().map(i -> userService.findAllRoles().get(i - 1)).collect(Collectors.toList()));
        if (userService.userAlreadyExist(user.getUsername()))
            return "new";
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.findOne(id));
        return "edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id, @RequestParam(value ="roles", required = false) List<Integer> idRoles){
        if (bindingResult.hasErrors())
            return "edit";
        if(idRoles != null)
            user.setRoles(idRoles.stream().map(i -> userService.findAllRoles().get(i - 1)).collect(Collectors.toList()));
        if (userService.userAlreadyExist(user.getUsername()))
            return "edit";
        userService.update(id,user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin";
    }

}
