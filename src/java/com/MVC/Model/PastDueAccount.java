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
public class PastDueAccount {
    
    private int pastDueAccountID;
    private int requestID;
    private double pastDueAmount;
    private Date dateSettled;
    private int reasonPastDue;
    private String reasonPastDueDesc;
    private String otherReason;
    private int recordedBy;
    private Date dateRecorded;
    private int active;

    public int getPastDueAccountID() {
        return pastDueAccountID;
    }

    public void setPastDueAccountID(int pastDueAccountID) {
        this.pastDueAccountID = pastDueAccountID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public double getPastDueAmount() {
        return pastDueAmount;
    }

    public void setPastDueAmount(double pastDueAmount) {
        this.pastDueAmount = pastDueAmount;
    }

    public Date getDateSettled() {
        return dateSettled;
    }

    public void setDateSettled(Date dateSettled) {
        this.dateSettled = dateSettled;
    }

    public int getReasonPastDue() {
        return reasonPastDue;
    }

    public void setReasonPastDue(int reasonPastDue) {
        this.reasonPastDue = reasonPastDue;
    }

    public String getReasonPastDueDesc() {
        return reasonPastDueDesc;
    }

    public void setReasonPastDueDesc(String reasonPastDueDesc) {
        this.reasonPastDueDesc = reasonPastDueDesc;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public int getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(int recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Date getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(Date dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
}
