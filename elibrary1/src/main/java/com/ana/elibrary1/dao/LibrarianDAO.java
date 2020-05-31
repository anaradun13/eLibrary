/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.dao;

import com.ana.elibrary1.beans.LibrarianBean;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ana.radun
 */

@Repository
@Transactional
public class LibrarianDAO {
    
    @Autowired
    SessionFactory sessionFactory;
    
    private Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }
    
    public String createLibrarian(LibrarianBean librarian) {
        Long success = (Long) getSession().save(librarian);
        if(success >=1) {
            return "Libraran with the id " + librarian.getId().toString() + " is successfully created!";
        }
        else {
            return "An error occured while saving librarian!";
        }
    }
    
    public String deleteLibrarian(LibrarianBean librarian) {
        LibrarianBean deleteBean = getSession().get(LibrarianBean.class, librarian.getId());
        if(deleteBean != null) {
            getSession().delete(deleteBean);
            return "Libraran with the id " + librarian.getId().toString() + " is successfully deleted!";
        }
        else {
            return "You are trying to delete an unexisting librarian!";
        }
    }
    
    public List<LibrarianBean> getAllLibrarians() {
        return getSession().createQuery("from LibrarianBean").list();
    }
    
    public List<LibrarianBean> viewLibrarian(Long id) {
        Query q = getSession().createQuery("from LibrarianBean where id = :id");
        q.setLong("id", id);
        return q.list();
    }
    
    public void editLibrarian(LibrarianBean librarianToUpdate) {
        getSession().update(librarianToUpdate);
    }
    
}
