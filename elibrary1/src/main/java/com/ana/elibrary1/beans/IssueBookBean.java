/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ana.elibrary1.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Type;

/**
 *
 * @author ana.radun
 */

@Entity
@Table(name = "issued_books")
public class IssueBookBean implements Serializable {

    private static final long serialVersionUID = 3968112725933765645L;
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;*/
    @Id
    private String studentId;
    private String callNumber, studentName, studentMobile;
    @Column
    @Type(type="date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date issuedDate;
    private String returnedStatus;
    
    public IssueBookBean() {
    }

    public IssueBookBean(String callNumber, String studentId, String studentName, String studentMobile) {
        this.callNumber = callNumber;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentMobile = studentMobile;
    }

    /*public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }*/

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentMobile() {
        return studentMobile;
    }

    public void setStudentMobile(String studentMobile) {
        this.studentMobile = studentMobile;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getReturnedStatus() {
        return returnedStatus;
    }

    public void setReturnedStatus(String returnedStatus) {
        this.returnedStatus = returnedStatus;
    }

}
