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
    private int requestID;
    private int loanReason;
    private String loanReasonDesc;
    private String otherReason;
    private int loanTermID;
    private LoanTerm loanTerm;

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

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

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public int getLoanTermID() {
        return loanTermID;
    }

    public void setLoanTermID(int loanTermID) {
        this.loanTermID = loanTermID;
    }

    public LoanTerm getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(LoanTerm loanTerm) {
        this.loanTerm = loanTerm;
    }
    
    
}
