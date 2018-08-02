/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;

/**
 *
 * @author Rey Christian
 */
public class Issue {
    
    private int id;
    private int issueType;
    private String issueTypeDesc;
    private int issuedTo;
    private int issuedBy;
    private int provOfficeCode;
    private int requestID;
    private int planID;
    private int pastDueAccountID;
    private Date dateRecorded;
    private Date dateResolved;
    private String findings;
    private String resolution;
    private boolean resolved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIssueType() {
        return issueType;
    }

    public void setIssueType(int issueType) {
        this.issueType = issueType;
    }

    public String getIssueTypeDesc() {
        return issueTypeDesc;
    }

    public void setIssueTypeDesc(String issueTypeDesc) {
        this.issueTypeDesc = issueTypeDesc;
    }

    public int getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(int issuedTo) {
        this.issuedTo = issuedTo;
    }

    public int getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(int issuedBy) {
        this.issuedBy = issuedBy;
    }
    
    public int getProvOfficeCode() {
        return provOfficeCode;
    }

    public void setProvOfficeCode(int provOfficeCode) {
        this.provOfficeCode = provOfficeCode;
    }
    
    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public int getPastDueAccountID() {
        return pastDueAccountID;
    }

    public void setPastDueAccountID(int pastDueAccountID) {
        this.pastDueAccountID = pastDueAccountID;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public Date getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(Date dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
    
    
}
