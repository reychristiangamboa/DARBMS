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
public class Repayment {
    
    private int repaymentID;
    private int requestID;
    private double amount;
    private Date dateRepayment;

    public int getRepaymentID() {
        return repaymentID;
    }

    public void setRepaymentID(int repaymentID) {
        this.repaymentID = repaymentID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateRepayment() {
        return dateRepayment;
    }

    public void setDateRepayment(Date dateRepayment) {
        this.dateRepayment = dateRepayment;
    }
    
        
    
}
