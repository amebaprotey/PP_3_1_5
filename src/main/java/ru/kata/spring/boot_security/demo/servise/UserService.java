package ru.kata.spring.boot_security.demo.servise;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public User findOne(int id);
    public void save(User user);

    public void update(int id, User user);

    public void delete(int id);
}
