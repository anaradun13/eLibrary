/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.controller;

import com.ana.elibrary1.beans.BookBean;
import com.ana.elibrary1.beans.IssueBookBean;
import com.ana.elibrary1.dao.BookDAO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BookController {
    
    @Autowired
    BookDAO bookDAO;
    
    @RequestMapping(value = "/createBook", method = RequestMethod.POST)
    @ResponseBody
    public String createBook(@RequestParam("callNumber") String callNumber, 
        @RequestParam("name") String name, @RequestParam("author") String author, 
        @RequestParam("publisher") String publisher, @RequestParam("quantity") Integer quantity) {
        String isSaved = null;
        try {
            BookBean book = new BookBean();
            book.setCallNumber(callNumber);
            book.setName(name);
            book.setAuthor(author);
            book.setPublisher(publisher);
            book.setQuantity(quantity);
            book.setIssued(0);
            isSaved = bookDAO.createBook(book);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isSaved;
    }
    
    @RequestMapping(value = "/deleteBook/{callNumber}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteBook(@PathVariable("callNumber") String callNumber) {
        String isDeleted;
        try {
            BookBean book = new BookBean();
            book.setCallNumber(callNumber);
            isDeleted = bookDAO.deleteBook(book);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isDeleted;
    }
    
    @RequestMapping(value = "/getAllBooks", method = RequestMethod.GET)
    @ResponseBody
    public List<BookBean> getAllBooks() {
        try {
            return bookDAO.getAllBooks();
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/issueBook", method = RequestMethod.POST)
    @ResponseBody
    public String issueBook(@RequestParam("id") String studentId, 
        @RequestParam("callNumber") String callNumber, 
        @RequestParam("mobile") String studentMobile,
        @RequestParam("name") String studentName) {
        String isIssued;
        try {
            IssueBookBean bookToIssue = new IssueBookBean();
            bookToIssue.setStudentId(studentId);
            bookToIssue.setCallNumber(callNumber);
            bookToIssue.setStudentName(studentName);
            bookToIssue.setStudentMobile(studentMobile);
            bookToIssue.setReturnedStatus("No");
            Date currentDate = new Date(System.currentTimeMillis());
            bookToIssue.setIssuedDate(currentDate);
            isIssued = bookDAO.issueBook(bookToIssue, studentId, callNumber);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isIssued;
    }
    
    @RequestMapping(value = "/viewIssuedBooks", method = RequestMethod.GET)
    @ResponseBody
    public List<IssueBookBean> viewIssuedBooks() {
        List<IssueBookBean> issuedBooks = null;
        try {
            issuedBooks = bookDAO.viewIssuedBooks();
            for (IssueBookBean b : issuedBooks) {
                b.getIssuedDate().toString();
            }
            return issuedBooks;
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/viewBook/{callNumber}", method = RequestMethod.GET)
    @ResponseBody
    public List<BookBean> viewBook(@PathVariable("callNumber") String callNumber) {
        try {
            return bookDAO.viewBook(callNumber);
        } catch (Exception e) {
            return null;
        }
    }
    
    @RequestMapping(value = "/returnBook", method = RequestMethod.PUT)
    @ResponseBody
    public String returnBook(@RequestParam("callNumber") String callNumber, 
            @RequestParam("studentId") String studentId) {
        String isReturned = null;
        try {
            isReturned = bookDAO.returnBook(callNumber, studentId);
        } catch(Exception ex) {
            return ex.getMessage();
        }
        return isReturned;
    }
    
    @RequestMapping(value = "/editBook", method = RequestMethod.PUT)
    @ResponseBody
    public List<BookBean> editBook(@RequestParam("callNumber") String callNumber, 
        @RequestParam("name") String name, @RequestParam("author") String author, 
        @RequestParam("publisher") String publisher, @RequestParam("quantity") Integer quantity) {
        BookBean bookToUpdate = null;
        try {
            bookToUpdate = viewBook(callNumber).get(0);
            bookToUpdate.setName(name);
            bookToUpdate.setAuthor(author);
            bookToUpdate.setPublisher(publisher);
            bookToUpdate.setQuantity(quantity);
            bookDAO.editBook(bookToUpdate);
            return viewBook(bookToUpdate.getCallNumber());
        } catch (Exception e) {
            return null;
        }
    }

}
