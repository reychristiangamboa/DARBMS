/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import com.MVC.DAO.CAPDEVDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rey Christian
 */
public class CAPDEVPlan {

    private int planID;
    private int requestID;
    private int pastDueAccountID;
    private String planDTN;
    private Date planDate;
    private Double budget;
    private int postponeReason;
    private String postponeReasonDesc;
    private String reason;
    private Date implementedDate;
    private int createdBy;
    private int assignedTo;
    private int approvedBy;
    private int planStatus;
    private String planStatusDesc;
    private String observations;
    private String recommendation;
    private int active;
    private int capdevAssignmentID;
    private int clusterID;

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

    public String getPlanDTN() {
        return planDTN;
    }

    public void setPlanDTN(String planDTN) {
        this.planDTN = planDTN;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public int getPostponeReason() {
        return postponeReason;
    }

    public void setPostponeReason(int postponeReason) {
        this.postponeReason = postponeReason;
    }

    public String getPostponeReasonDesc() {
        return postponeReasonDesc;
    }

    public void setPostponeReasonDesc(String postponeReasonDesc) {
        this.postponeReasonDesc = postponeReasonDesc;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getImplementedDate() {
        return implementedDate;
    }

    public void setImplementedDate(Date implementedDate) {
        this.implementedDate = implementedDate;
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

    public String getPlanStatusDesc() {
        return planStatusDesc;
    }

    public void setPlanStatusDesc(String plantStatusDesc) {
        this.planStatusDesc = plantStatusDesc;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public ArrayList<CAPDEVActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<CAPDEVActivity> activities) {
        this.activities = activities;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    public int getCapdevAssignmentID() {
        return capdevAssignmentID;
    }

    public void setCapdevAssignmentID(int capdevAssignmentID) {
        this.capdevAssignmentID = capdevAssignmentID;
    }

    public ArrayList<Integer> getAllAttenededParticipants() {

        ArrayList<Integer> filtered = new ArrayList();

        for (CAPDEVActivity act : this.activities) {
            for (ARB arb : act.getArbList()) {
                if (arb.getIsPresent() > 0) {
                    if (!filtered.contains(arb.getArbID())) {
                        filtered.add(arb.getArbID());
                    }
                }
            }
        }

        return filtered;
    }

    public boolean checkAllActivitiesHaveParticipants() {
        for (CAPDEVActivity act : this.activities) {
            if (act.getArbList().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean assessmentsAreComplete() {
        for (CAPDEVActivity act : this.activities) {
            if (act.getActive() == 0) { // 1 activity is still left unassessed
                return false;
            }
        }
        return true;
    }
    
    public boolean hasAPCPOrientation() {
        CAPDEVDAO dao = new CAPDEVDAO();
        this.activities = dao.getCAPDEVPlanActivities(this.planID);
        for (CAPDEVActivity act : this.activities) {
            if (act.getActivityType() == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPreReleaseOrientation() {
        CAPDEVDAO dao = new CAPDEVDAO();
        this.activities = dao.getCAPDEVPlanActivities(this.planID);
        for (CAPDEVActivity act : this.activities) {
            if (act.getActivityType() == 3) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkIfPlanIsOnTrack(Date date) {

        if (getDateDiff(date) < 5) {
            System.out.println("IT'S STILL ON TRACK!!");
            return true;
        }
        System.out.println("IT'S NOT ON TRACK!!");
        return false;
    }

    public int getDateDiff(Date date1) {
        long diffInMillies = date1.getTime() - System.currentTimeMillis();
        System.out.println((int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
    
    public boolean isPlanForReschedule(){
        CAPDEVDAO dao = new CAPDEVDAO();
        this.activities = dao.getCAPDEVPlanActivities(this.planID);
        
        for(CAPDEVActivity act : this.activities){
            if(act.getActive() == 1 && this.planStatus == 2){
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<ARB> getMandatoryAbsentees(){
        
        ArrayList<ARB> absentees = new ArrayList();
        CAPDEVDAO dao = new CAPDEVDAO();
        
        this.activities = dao.getCAPDEVPlanActivities(this.planID);
        
        for(CAPDEVActivity act : this.activities){
            if(act.getActivityType() == 2 || act.getActivityType() == 3){
                for(ARB arb : act.getArbList()){
                    if(!dao.hasAttendedMandatoryActivities(arb.getArbID(), this.planID)){
                        if(!absentees.contains(arb)){
                            absentees.add(arb);
                        }
                    }
                }
            }
        }
        
        return absentees;
        
    }

}
