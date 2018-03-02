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
import com.MVC.DAO.UserDAO;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Rey Christian
 */
public class Log {
    private int logID;
    private int actionType;
    private int actionBy;
    private Date date;
    private Time time;
    private String actionDesc;
    
    
    public Log(){
        
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getActionBy() {
        return actionBy;
    }

    public void setActionBy(int actionBy) {
        this.actionBy = actionBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }
    
    public String getActionByDesc(){
        UserDAO uDAO = new UserDAO();
        User u = uDAO.searchUser(this.actionBy);
        return u.getFullName();
    }
    
    
}
