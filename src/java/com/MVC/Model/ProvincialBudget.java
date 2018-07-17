/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Rey Christian
 */
public class ProvincialBudget {
    
    private int id;
    private int provOfficeCode;
    private double budget;
    private int requestedBy;
    private int approvedBy;
    private String reason;
    private Date startDate;
    private Date endDate;
    private boolean isDisapproved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvOfficeCode() {
        return provOfficeCode;
    }

    public void setProvOfficeCode(int provOfficeCode) {
        this.provOfficeCode = provOfficeCode;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(int requestedBy) {
        this.requestedBy = requestedBy;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIsDisapproved() {
        return isDisapproved;
    }

    public void setIsDisapproved(boolean isDisapproved) {
        this.isDisapproved = isDisapproved;
    }
    
    
    public double getRemainingAPCPBudget(ArrayList<APCPRequest> requestList){
        
        double budget = this.budget;
        
        for(APCPRequest req : requestList){
            budget -= req.getLoanAmount();
        }
        
        return budget;
    }
    
    public double getRemainingCAPDEVBudget(ArrayList<CAPDEVPlan> planList){
        
        double budget = this.budget;
        
        for(CAPDEVPlan plan : planList){
            budget -= plan.getBudget();
        }
        
        return budget;
    }

    
    
}
