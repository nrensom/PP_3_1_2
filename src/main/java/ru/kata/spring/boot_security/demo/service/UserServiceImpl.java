package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;


import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return true;
    }

    public boolean delete(long id) {
        userDao.delete(id);
        return true;
    }

    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public Long getUserId(String username) {
        return userDao.findByUsername(username).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }


    @Override
    public Set<Role> getRoles(List<Long> roles) {
        Set<Role> result = new HashSet<>();
        for (Long role : roles) {
            if (role == 1) {
                result.add(new Role(1, "ROLE_ADMIN"));
            } else if (role == 2) {
                result.add(new Role(2, "ROLE_USER"));
            }
        }
        return result;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }
}
