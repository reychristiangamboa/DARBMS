/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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
    
    public int getDaysUnsettled(){
        Calendar settled = Calendar.getInstance();
        
        Calendar recorded = Calendar.getInstance();
        recorded.setTime(this.dateRecorded);
        
        Calendar today = Calendar.getInstance();
        long days = 0;
        if(this.dateSettled != null){
            settled.setTime(this.dateSettled);
            days = daysBetween (recorded,settled);
        } else {
            days = daysBetween (recorded,settled);
        }
         
        
        return (int)days;
    }
    
    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
    
    public String getCreditStanding(int daysDiff){
        String color = ""; 
        if(daysDiff >= 0 && daysDiff <= 30){
            color = "green";
        } else if (daysDiff >= 31 && daysDiff <= 60){
            color = "yellow";
        } else if (daysDiff >= 61 && daysDiff <= 89) {
            color = "orange";
        } else if (daysDiff >= 90){
            color = "red";
        }
        
        return color;
    }
    
}
