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
public class ARB {
    
    private int arbID;
    private int arboID;
    private int arboRepresentative;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date memberSince;
    private String arbUnitNumStreet;
    private int brgyCode;
    private String brgyDesc;
    private int cityMunCode;
    private String cityMunDesc;
    private int provCode;
    private String provDesc;
    private int regCode;
    private String regDesc;
    private ArrayList<Dependent> dependents = new ArrayList();
    private ArrayList<Crop> crops = new ArrayList();
    private String gender;
    private int educationLevel;
    private String educationLevelDesc;
    private double landArea;
    private double arbRating;
    private int arbStatus;
    private String arbStatusDesc;
    private int arbActive;
    private int isPresent;
    

    public ARB() {
    }

    public int getArbID() {
        return arbID;
    }

    public void setArbID(int arbID) {
        this.arbID = arbID;
    }

    public int getArboID() {
        return arboID;
    }

    public void setArboID(int arboID) {
        this.arboID = arboID;
    }

    public int getArboRepresentative() {
        return arboRepresentative;
    }

    public void setArboRepresentative(int arboRepresentative) {
        this.arboRepresentative = arboRepresentative;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
    }

    public String getArbUnitNumStreet() {
        return arbUnitNumStreet;
    }

    public void setArbUnitNumStreet(String arbUnitNumStreet) {
        this.arbUnitNumStreet = arbUnitNumStreet;
    }

    public int getBrgyCode() {
        return brgyCode;
    }

    public void setBrgyCode(int brgyCode) {
        this.brgyCode = brgyCode;
    }

    public String getBrgyDesc() {
        return brgyDesc;
    }

    public void setBrgyDesc(String brgyDesc) {
        this.brgyDesc = brgyDesc;
    }

    public int getCityMunCode() {
        return cityMunCode;
    }

    public void setCityMunCode(int cityMunCode) {
        this.cityMunCode = cityMunCode;
    }

    public String getCityMunDesc() {
        return cityMunDesc;
    }

    public void setCityMunDesc(String cityMunDesc) {
        this.cityMunDesc = cityMunDesc;
    }

    public int getProvCode() {
        return provCode;
    }

    public void setProvCode(int provCode) {
        this.provCode = provCode;
    }

    public String getProvDesc() {
        return provDesc;
    }

    public void setProvDesc(String provDesc) {
        this.provDesc = provDesc;
    }

    public int getRegCode() {
        return regCode;
    }

    public void setRegCode(int regCode) {
        this.regCode = regCode;
    }

    public String getRegDesc() {
        return regDesc;
    }

    public void setRegDesc(String regDesc) {
        this.regDesc = regDesc;
    }

    public ArrayList<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(ArrayList<Dependent> dependents) {
        this.dependents = dependents;
    }

    public ArrayList<Crop> getCrops() {
        return crops;
    }

    public void setCrops(ArrayList<Crop> crops) {
        this.crops = crops;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
    
    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public double getArbRating() {
        return arbRating;
    }

    public void setArbRating(double arbRating) {
        this.arbRating = arbRating;
    }

    public int getArbStatus() {
        return arbStatus;
    }

    public void setArbStatus(int arbStatus) {
        this.arbStatus = arbStatus;
    }

    public String getArbStatusDesc() {
        return arbStatusDesc;
    }

    public void setArbStatusDesc(String arbStatusDesc) {
        this.arbStatusDesc = arbStatusDesc;
    }
    
    public int getArbActive() {
        return arbActive;
    }

    public void setArbActive(int arbActive) {
        this.arbActive = arbActive;
    }
    
    public String getFullName(){
        return this.firstName + " " + this.middleName + " " + this.lastName;
    }
    
    public String getFLName(){
        return this.firstName + " " + this.lastName;
    }
    
    public String getFullAddress(){
        return this.arbUnitNumStreet + ", " + this.cityMunDesc + ", " + this.brgyDesc + ", " + this.provDesc + ", " + this.regDesc;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }
    
    
    
    public String printAllCrops(){
        StringBuilder sb = new StringBuilder();
        for(Crop c : crops){
            sb.append(c.getCropTypeDesc() + " ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.middleName + " " + this.lastName + " " + this.gender + " " + this.arbUnitNumStreet + " " + this.landArea + " " + this.memberSince + " " + this.educationLevel;
    }
    
    

}
