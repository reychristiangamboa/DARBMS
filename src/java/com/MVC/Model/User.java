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
public class User {
    private int userID;
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String contactNo;
    private int userType;
    private String userTypeDesc;
    private int active;
    private int provOfficeCode;
    private String provOfficeDesc;
    private int regOfficeCode;
    private String regDesc;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserTypeDesc() {
        return userTypeDesc;
    }

    public void setUserTypeDesc(String userTypeDesc) {
        this.userTypeDesc = userTypeDesc;
    }
    
    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getProvOfficeCode() {
        return provOfficeCode;
    }

    public void setProvOfficeCode(int provOfficeCode) {
        this.provOfficeCode = provOfficeCode;
    }

    public String getProvOfficeDesc() {
        return provOfficeDesc;
    }

    public void setProvOfficeDesc(String provOfficeDesc) {
        this.provOfficeDesc = provOfficeDesc;
    }

    public int getRegOfficeCode() {
        return regOfficeCode;
    }

    public void setRegOfficeCode(int regOfficeCode) {
        this.regOfficeCode = regOfficeCode;
    }

    public String getRegDesc() {
        return regDesc;
    }

    public void setRegDesc(String regDesc) {
        this.regDesc = regDesc;
    }
    
}
