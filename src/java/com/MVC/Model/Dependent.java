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
public class Dependent {
    
    public String name;
    public Date birthday;
    public int educationLevel;
    public String educationLevelDesc;
    private int relationshipType;
    private String relationshipTypeDesc;
    public int arbID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(int educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getEducationLevelDesc() {
        return educationLevelDesc;
    }

    public void setEducationLevelDesc(String educationLevelDesc) {
        this.educationLevelDesc = educationLevelDesc;
    }

    public int getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(int relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getRelationshipTypeDesc() {
        return relationshipTypeDesc;
    }

    public void setRelationshipTypeDesc(String relationshipTypeDesc) {
        this.relationshipTypeDesc = relationshipTypeDesc;
    }
    
    public int getArbID() {
        return arbID;
    }

    public void setArbID(int arbID) {
        this.arbID = arbID;
    }
    
    
    
}
