package ru.kata.spring.boot_security.demo.servise;



import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<Role> findAllRoles();
    User findOne(int id);
    void save(User user);

    void update(int id, User user);

    void delete(int id);
    boolean userAlreadyExist(String username);
    User findByUsername(String username);
}
