    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;
import java.util.ArrayList;
import com.MVC.Model.ARB;

/**
 *
 * @author Rey Christian
 */
public class CAPDEVActivity {

    private int activityID;
    private int planID;
    private int activityType;
    private int activityCategory;
    private String activityCategoryDesc;
    private String technicalAssistant;
    private Date activityDate;
    private Date implementedDate;
    private String observations;
    private String recommendation;
    private String activityName;
    private String activityDesc;
    private String activityReportDTN;
    private int isPresent;
    private int active;
    private ArrayList<ARB> arbList = new ArrayList();

    public ArrayList<ARB> getArbList() {
        return arbList;
    }

    public void setArbList(ArrayList<ARB> arbList) {
        this.arbList = arbList;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(int activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivityCategoryDesc() {
        return activityCategoryDesc;
    }

    public void setActivityCategoryDesc(String activityCategoryDesc) {
        this.activityCategoryDesc = activityCategoryDesc;
    }
    
    public String getTechnicalAssistant() {
        return technicalAssistant;
    }

    public void setTechnicalAssistant(String technicalAssistant) {
        this.technicalAssistant = technicalAssistant;
    }
    
    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getActivityReportDTN() {
        return activityReportDTN;
    }

    public void setActivityReportDTN(String activityReportDTN) {
        this.activityReportDTN = activityReportDTN;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }
    
    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Date getImplementedDate() {
        return implementedDate;
    }

    public void setImplementedDate(Date implementedDate) {
        this.implementedDate = implementedDate;
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
    
    public double getAttendanceRate(ArrayList<CAPDEVActivity> activities){
        double present = 0;
        double absent = 0;
        double rate = 0;
        
        for(CAPDEVActivity cAct : activities){
            if(cAct.getIsPresent() == 0){
                absent++;
            } else if(cAct.getIsPresent() == 1){
                present++;
            }
        }
        
        rate = (present/activities.size()) * 100;
        
        return rate;
    }
    public int getAttendance(ArrayList<CAPDEVActivity> activities){
        int present = 0;
        
        for(CAPDEVActivity cAct : activities){
            if(cAct.getIsPresent() == 1){
                present++;
            }
        }
        
        return present;
    }
    
}
