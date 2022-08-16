package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
@Transactional
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager em;
    @Override
    public boolean save(Role role) {
        em.persist(role);
        return true;
    }
}
