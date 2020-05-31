/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.dao;

import com.ana.elibrary1.beans.AppUserBean;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ana.radun
 */

@Repository
@Transactional
public class AppUserDAO {
    
    @Autowired
    SessionFactory sessionFactory;
    
    @Value("${admin.pages}")
    private String ADMIN_PAGES;
    
    @Value("${librarian.pages}")
    private String LIBRARIAN_PAGES;
    
    private Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }
    
    public String createUser(AppUserBean user) {
        Long success = (Long) getSession().save(user);
        if(success >=1) {
            return "User with the id " + user.getId().toString() + " is successfully created!";
        }
        else {
            return "An error occured while saving user!";
        }
    }
    
    public String deleteUser(AppUserBean user) {
        AppUserBean deleteBean = getSession().get(AppUserBean.class, user.getId());
        if(deleteBean != null) {
            getSession().delete(deleteBean);
            return "User with the id " + user.getId().toString() + " is successfully deleted!";
        }
        else {
            return "You are trying to delete an unexisting user!";
        }
    }
    
    public List<AppUserBean> getAllUsers() {
        return getSession().createQuery("from AppUserBean").list();
    }
    
    public List<AppUserBean> viewUser(Long id) {
        Query q = getSession().createQuery("from AppUserBean where id = :id");
        q.setLong("id", id);
        return q.list();
    }
    
    public void editUser(AppUserBean userToUpdate) {
        getSession().update(userToUpdate);
    }
    
    public String[] logIn(String email, String password) {
        Query q = getSession().createQuery("from AppUserBean where email = :email");
        q.setString("email", email);
        AppUserBean existingUser = (AppUserBean) q.list().get(0);
        if(password.equals(existingUser.getPassword())) {
            if(existingUser.isAdministrator() == true && existingUser.isLibrarian() == false) {
                return ADMIN_PAGES.split(", ");
            }
            else if(existingUser.isAdministrator() == false && existingUser.isLibrarian() == true) {
                return LIBRARIAN_PAGES.split(", ");
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
    
}
