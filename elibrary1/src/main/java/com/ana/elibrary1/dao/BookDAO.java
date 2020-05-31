/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.dao;

import com.ana.elibrary1.beans.BookBean;
import com.ana.elibrary1.beans.IssueBookBean;
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
public class BookDAO {
    
    @Autowired
    SessionFactory sessionFactory;
    
    private Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }
    
    public String createBook(BookBean book) {
        String success = (String) getSession().save(book);
        if(success != null) {
            return "Book with the call number '" + book.getCallNumber() + "' is successfully created!";
        }
        else {
            return "An error occured while saving book!";
        }
    }
    
    public String deleteBook(BookBean book) {
        BookBean testBean = getSession().get(BookBean.class, book.getCallNumber());
        if(testBean.getIssued() == 0) {
            getSession().delete(testBean);
            return "Book with the id " + book.getCallNumber() + " is successfully deleted!";
        }
        else if(testBean.getIssued() != 0) {
            return "This book is issued and can't be deleted!";
        }
        else {
            return "You are trying to delete an unexisting book!";
        }
    }
    
    public List<BookBean> getAllBooks() {
        return getSession().createQuery("from BookBean").list();
    }
    
    public List<BookBean> viewBook(String callNumber) {
        Query q = getSession().createQuery("from BookBean where callNumber = :callNumber");
        q.setString("callNumber", callNumber);
        return q.list();
    }
    
    public int checkIssuded(String callNo) {
        Query q = (Query) getSession().createQuery("from BookBean where callNumber = :callNumber and quantity>issued");
        q.setString("callNumber", callNo);
        return q.list().size();
    }

    public String issueBook(IssueBookBean bookToIssue, String studentId, String callNumber) {
        int available = checkIssuded(bookToIssue.getCallNumber());
        String success = null;
        BookBean book = getSession().get(BookBean.class, callNumber);
        if(available > 0) {
            IssueBookBean existing = getSession().get(IssueBookBean.class, studentId);
            book.setIssued(book.getIssued()+1);
            getSession().update(book);
            if(existing == null) {
                success = (String) getSession().save(bookToIssue);
            }
            else if(existing != null && existing.getReturnedStatus().equalsIgnoreCase("yes")) {
                try{
                    IssueBookBean oldRecord = getSession().get(IssueBookBean.class, studentId);
                    oldRecord.setIssuedDate(bookToIssue.getIssuedDate());
                    oldRecord.setReturnedStatus(bookToIssue.getReturnedStatus());
                    getSession().update(oldRecord);
                    success = "yes";
                }
                catch(Exception e) {
                    return e.getMessage();
                }
                System.out.println(success);
            }
            if(success != null) {
                    return "Book with the call number '" + bookToIssue.getCallNumber() + "' is successfully issued!";
            }
            else {
                return "An error occured while issuing the book!";
            }
        }
        else {
            return "There is no available book to issue!";
        }
    }
    
    public List<IssueBookBean> viewIssuedBooks() {
        Query q = getSession().createQuery("from IssueBookBean");
        return q.list();
    }
    
    public String returnBook(String callNumber, String StudentId) {
        IssueBookBean bookToReturn = getSession().get(IssueBookBean.class, StudentId);
        BookBean book = getSession().get(BookBean.class, callNumber);
        bookToReturn.setReturnedStatus("Yes");
        getSession().update(bookToReturn);
        book.setIssued(book.getIssued()-1);
        getSession().update(book);
        String success = getSession().get(IssueBookBean.class, bookToReturn.getStudentId()).getReturnedStatus();
        if(success.equalsIgnoreCase("yes")) {
                return "Book with the call number '" + bookToReturn.getCallNumber() + "' is successfully returned!";
            }
            else {
                return "An error occured while returning the book!";
            }
    }
    
    public void editBook(BookBean bookToUpdate) {
        getSession().update(bookToUpdate);
    }
    
}
