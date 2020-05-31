/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.controller;

import com.ana.elibrary1.beans.AppUserBean;
import com.ana.elibrary1.dao.AppUserDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ana.radun
 */

@Controller
public class AppUserController {
    
    @Autowired
    AppUserDAO appUserDAO;
    
    @Value("${admin.pages}")
    private String ADMIN_PAGES;
    
    @Value("${librarian.pages}")
    private String LIBRARIAN_PAGES;
    
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam("name") String name, 
            @RequestParam("email") String email, @RequestParam("password") String password, 
            @RequestParam("mobile") Integer mobile, @RequestParam("admin") boolean admin, 
            @RequestParam("librarian") boolean librarian) {
        String isCreated;
        try {
            AppUserBean user = new AppUserBean();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setMobile(mobile);
            user.setAdministrator(admin);
            user.setLibrarian(librarian);
            isCreated = appUserDAO.createUser(user);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isCreated;
    }
    
    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@PathVariable("id") Long id) {
        String isDeleted;
        try {
            AppUserBean user = new AppUserBean();
            user.setId(id);
            isDeleted = appUserDAO.deleteUser(user);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isDeleted;
    }
    
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public List<AppUserBean> getAllUsers() {
        try {
            return appUserDAO.getAllUsers();
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/viewUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<AppUserBean> viewUser(@PathVariable("id") Long id) {
        try {
            return appUserDAO.viewUser(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/editUser", method = RequestMethod.PUT)
    @ResponseBody
    public List<AppUserBean> editUser(@RequestParam("id") Long id, 
            @RequestParam("name") String name, 
            @RequestParam("email") String email, @RequestParam("password") String password, 
            @RequestParam("mobile") Integer mobile, @RequestParam("admin") boolean admin) {
        AppUserBean userToUpdate = null;
        try {
            userToUpdate = viewUser(id).get(0);
            userToUpdate.setName(name);
            userToUpdate.setEmail(email);
            userToUpdate.setPassword(password);
            userToUpdate.setMobile(mobile);
            appUserDAO.editUser(userToUpdate);
            return viewUser(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/logIn", method = RequestMethod.GET)
    @ResponseBody
    public String[] logIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            return appUserDAO.logIn(email, password);
        } catch (Exception e) {
            return null;
        }
    }

    
}
