/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

/**
 *
 * @author Rey Christian
 */
public class LoanReason {
    
    private int loanReason;
    private String loanReasonDesc;

    public int getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(int loanReason) {
        this.loanReason = loanReason;
    }

    public String getLoanReasonDesc() {
        return loanReasonDesc;
    }

    public void setLoanReasonDesc(String loanReasonDesc) {
        this.loanReasonDesc = loanReasonDesc;
    }
    
}
