package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public SetupDataLoader(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Iterable<User> users = userService.getAllUsers();
        if (users.iterator().hasNext()) {
            return;
        }

        Role adminRole = new Role("ROlE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        roleDao.save(adminRole);
        roleDao.save(userRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setRoles(adminRoles);
        userService.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setName("Peppa");
        user.setLastName("Pig");
        user.setAge((byte) 4);
        user.setDepartment("what");
        user.setPassword("user");
        user.setRoles(userRoles);
        userService.save(user);

    }
}
