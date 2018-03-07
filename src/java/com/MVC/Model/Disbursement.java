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
public class Disbursement {

    private int disbursementID;
    private int releaseID;
    private int arbID;
    private double disbursedAmount;
    private double OSBalance;
    private Date dateDisbursed;
    private int disbursedBy;

    public int getDisbursementID() {
        return disbursementID;
    }

    public void setDisbursementID(int disbursementID) {
        this.disbursementID = disbursementID;
    }

    public int getReleaseID() {
        return releaseID;
    }

    public void setReleaseID(int releaseID) {
        this.releaseID = releaseID;
    }
    
    public int getArbID() {
        return arbID;
    }

    public void setArbID(int arbID) {
        this.arbID = arbID;
    }

    public double getDisbursedAmount() {
        return disbursedAmount;
    }

    public void setDisbursedAmount(Double amount) {
        this.disbursedAmount = amount;
    }

    public double getOSBalance() {
        return OSBalance;
    }

    public void setOSBalance(double OSBalance) {
        this.OSBalance = OSBalance;
    }
    
    public Date getDateDisbursed() {
        return dateDisbursed;
    }

    public void setDateDisbursed(Date dateDisbursed) {
        this.dateDisbursed = dateDisbursed;
    }

    public int getDisbursedBy() {
        return disbursedBy;
    }

    public void setDisbursedBy(int disbursedBy) {
        this.disbursedBy = disbursedBy;
    }
    
    

}