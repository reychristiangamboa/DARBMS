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
public class LoanTerm {
    
    private int loanTerm;
    private String loanTermDesc;
    private double arboInterestRate;
    private double arbInterestRate;
    private double pastDueInterestRate;

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getLoanTermDesc() {
        return loanTermDesc;
    }

    public void setLoanTermDesc(String loanTermDesc) {
        this.loanTermDesc = loanTermDesc;
    }

    public double getArboInterestRate() {
        return arboInterestRate;
    }

    public void setArboInterestRate(double arboInterestRate) {
        this.arboInterestRate = arboInterestRate;
    }

    public double getArbInterestRate() {
        return arbInterestRate;
    }

    public void setArbInterestRate(double arbInterestRate) {
        this.arbInterestRate = arbInterestRate;
    }

    public double getPastDueInterestRate() {
        return pastDueInterestRate;
    }

    public void setPastDueInterestRate(double pastDueInterestRate) {
        this.pastDueInterestRate = pastDueInterestRate;
    }
    
    
}
