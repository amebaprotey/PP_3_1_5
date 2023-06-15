package ru.kata.spring.boot_security.demo.createUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.servise.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class CreateUser {
    private UserServiceImpl userServiceImpl;
    private RoleRepository roleRepository;
    @Autowired
    public void setUserServiceImp(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @Autowired
    public void setRoleRepositories(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init(){
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser  = new Role("ROLE_USER");
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        Set<Role> roleListAdmin = new HashSet<>();
        roleListAdmin.add(roleAdmin);

        Set<Role> roleListUser = new HashSet<>();
        roleListUser.add(roleUser);

        User admin = new User("admin","admin","Админ","Админ", roleListAdmin);
        User user = new User("user","user", "Пользователь","Пользователь", roleListUser);
        userServiceImpl.save(admin);
        userServiceImpl.save(user);
    }
}
