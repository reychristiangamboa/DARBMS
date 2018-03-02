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

    private int planID;
    private int activityID;
    private Date activityDate;
    private String activityName;
    private String activityDesc;
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

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
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

}