/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.controller;

import com.ana.elibrary1.beans.LibrarianBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ana.elibrary1.dao.LibrarianDAO;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ana.radun
 */

@Controller
public class LibrarianController {
    
    @Autowired
    LibrarianDAO librarianDAO;
    
    @RequestMapping(value = "/createLibrarian", method = RequestMethod.POST)
    @ResponseBody
    public String createLibrarian(@RequestParam("name") String name, 
            @RequestParam("email") String email, @RequestParam("password") String password, 
            @RequestParam("mobile") Integer mobile) {
        String isCreated;
        try {
            LibrarianBean librarian = new LibrarianBean();
            librarian.setName(name);
            librarian.setEmail(email);
            librarian.setPassword(password);
            librarian.setMobile(mobile);
            isCreated = librarianDAO.createLibrarian(librarian);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isCreated;
    }
    
    @RequestMapping(value = "/deleteLibrarian/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteLibrarian(@PathVariable("id") Long id) {
        String isDeleted;
        try {
            LibrarianBean librarian = new LibrarianBean();
            librarian.setId(id);
            isDeleted = librarianDAO.deleteLibrarian(librarian);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isDeleted;
    }
    
    @RequestMapping(value = "/getAllLibrarians", method = RequestMethod.GET)
    @ResponseBody
    public List<LibrarianBean> getAllLibrarians() {
        try {
            return librarianDAO.getAllLibrarians();
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/viewLibrarian/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<LibrarianBean> viewLibrarian(@PathVariable("id") Long id) {
        try {
            return librarianDAO.viewLibrarian(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/editLibrarian", method = RequestMethod.PUT)
    @ResponseBody
    public List<LibrarianBean> editLibrarian(@RequestParam("id") Long id, 
            @RequestParam("name") String name, 
            @RequestParam("email") String email, @RequestParam("password") String password, 
            @RequestParam("mobile") Integer mobile) {
        LibrarianBean librarianToUpdate = null;
        try {
            librarianToUpdate = viewLibrarian(id).get(0);
            librarianToUpdate.setName(name);
            librarianToUpdate.setEmail(email);
            librarianToUpdate.setPassword(password);
            librarianToUpdate.setMobile(mobile);
            librarianDAO.editLibrarian(librarianToUpdate);
            return viewLibrarian(id);
        } catch (Exception e) {
            return null;
        }
    }

    
}
