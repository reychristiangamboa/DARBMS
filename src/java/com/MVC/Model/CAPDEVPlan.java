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
public class CAPDEVPlan {
    
    private int planID;
    private int requestID;
    private int pastDueAccountID;
    private int assignedTo;
    private String planDTN;
    private int createdBy;
    private int approvedBy;
    private int planStatus;
    private String plantStatusDesc;
    private ArrayList<CAPDEVActivity> activities = new ArrayList();
    

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getPastDueAccountID() {
        return pastDueAccountID;
    }

    public void setPastDueAccountID(int pastDueAccountID) {
        this.pastDueAccountID = pastDueAccountID;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getPlanDTN() {
        return planDTN;
    }

    public void setPlanDTN(String planDTN) {
        this.planDTN = planDTN;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    
    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlantStatusDesc() {
        return plantStatusDesc;
    }

    public void setPlantStatusDesc(String plantStatusDesc) {
        this.plantStatusDesc = plantStatusDesc;
    }
    
    public ArrayList<CAPDEVActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<CAPDEVActivity> activities) {
        this.activities = activities;
    }
    
    
    
    
}


