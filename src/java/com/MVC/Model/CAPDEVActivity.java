/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
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

    public double getAttendanceRate(ArrayList<CAPDEVActivity> activities) {
        double present = 0;
        double absent = 0;
        double rate = 0;

        for (CAPDEVActivity cAct : activities) {
            if (cAct.getIsPresent() == 0) {
                absent++;
            } else if (cAct.getIsPresent() == 1) {
                present++;
            }
        }

        rate = (present / activities.size()) * 100;

        return rate;
    }

    public int getAttendance(ArrayList<CAPDEVActivity> activities) {
        int present = 0;

        for (CAPDEVActivity cAct : activities) {
            if (cAct.getIsPresent() == 1) {
                present++;
            }
        }

        return present;
    }

    public boolean getNewAccessingCategories() {

        if (this.activityCategory == 2 || this.activityCategory == 5 || this.activityCategory == 7 || this.activityCategory == 9 || this.activityCategory == 10) {
            return true;
        }

        return false;

    }

    public boolean getExistingCategories() {

        if (this.activityCategory == 3 || this.activityCategory == 6 || this.activityCategory == 8 || this.activityCategory == 9 || this.activityCategory == 10) {
            return true;
        }

        return false;

    }

    public ArrayList<ARB> getNonParticipantARBs() { // retrieves ARBs that are not participant/s of activity
        int arboID = this.arbList.get(0).getArboID(); // assuming every activity has a participant
        ARBDAO arbDAO = new ARBDAO();

        ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(arboID); // retrieves all ARBs of that ARBO
        ArrayList<ARB> filtered = new ArrayList();

        for (ARB arb : arbList) {
            for (ARB participant : this.arbList) {
                if (arb.getArbID() != participant.getArbID()) { // if this activity doesn't recognize this ARB 
                    filtered.add(arb);                          // as a participant, ADD to filtered
                }
            }
        }

        return filtered;
    }

}
