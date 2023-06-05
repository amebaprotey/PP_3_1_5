package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptionHandling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.exceptionHandling.UserIncorrectData;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servise.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class UserController {

    private UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User show(@PathVariable("id") int id){
        User user = userService.findOne(id);
        if (user == null){
            throw new NoSuchUserException("There is no user with ID = " +
                    id + " in Database");
        }
        return userService.findOne(id);
    }
    @PostMapping("/users")
    public User addNewUser(@RequestBody User user){
        userService.save(user);
        return user;
    }
    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user){
        userService.update(user.getId(),user);
        return user;
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id){
        User user = userService.findOne(id);
        if (user == null){
            throw new NoSuchUserException("There is no user with ID = " +
                    id + " in Database");
        }
        userService.delete(id);
        return "User with ID = " + id + " was deleted";
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        List<Role> allRoles = userService.findAllRoles();
        return allRoles;
    }
    @GetMapping("/viewUser")
    public User showUser(Principal principal) {
        User users = userService.findByUsername(principal.getName());
        return users;
    }
//    @GetMapping()
//    public String getUsers(Principal principal, ModelMap model) {
//        model.addAttribute("thisUser", userService.loadUserByUsername(principal.getName()));
//        model.addAttribute("users", userService.findAll());
//        return "admin";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                         @RequestParam(value ="roles", required = false) List<Integer> idRoles){
//        if (bindingResult.hasErrors())
//            return "admin";
//        if(idRoles != null)
//            user.setRoles(idRoles.stream().map(i -> userService.findAllRoles().get(i - 1)).collect(Collectors.toList()));
//        if (userService.userAlreadyExist(user.getUsername()))
//            return "redirect:/admin";
//        userService.save(user);
//        return "redirect:/admin";
//    }
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
//                         @PathVariable("id") int id){
//        if (bindingResult.hasErrors())
//            return "admin";
//        if (!userService.findOne(id).getUsername().equals(user.getUsername()) & userService.userAlreadyExist(user.getUsername()))
//            return "redirect:/admin";
//        userService.update(id,user);
//        return "redirect:/admin";
//    }
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id){
//        userService.delete(id);
//        return "redirect:/admin";
//    }

}
