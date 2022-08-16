package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(long id) {
        em.createQuery("delete from User where id=:id").setParameter("id", id).executeUpdate();
    }


    @Override
    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username=:username", User.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public User getUser(long id) {
        return em.find(User.class, id);
    }
}
