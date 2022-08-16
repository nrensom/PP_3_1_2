package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;


public interface UserDao {
    public List<User> getAllUsers();

    public void save(User user);

    public void update(User user);

    public void delete(long id);

    public User getUser(long id);

    public User findByUsername(String username);
}
