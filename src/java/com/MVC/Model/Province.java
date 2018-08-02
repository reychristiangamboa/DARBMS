/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.util.ArrayList;

/**
 *
 * @author Rey Christian
 */
public class Province {
    
    private int provCode;
    private String provDesc;
    private int regCode;
    private String regDesc;
    private ArrayList<ProvincialBudget> provincialAPCPBudget;
    private ArrayList<ProvincialBudget> provincialCAPDEVBudget;

    public ArrayList<ProvincialBudget> getProvincialAPCPBudget() {
        return provincialAPCPBudget;
    }

    public void setProvincialAPCPBudget(ArrayList<ProvincialBudget> provincialAPCPBudget) {
        this.provincialAPCPBudget = provincialAPCPBudget;
    }

    public ArrayList<ProvincialBudget> getProvincialCAPDEVBudget() {
        return provincialCAPDEVBudget;
    }

    public void setProvincialCAPDEVBudget(ArrayList<ProvincialBudget> provincialCAPDEVBudget) {
        this.provincialCAPDEVBudget = provincialCAPDEVBudget;
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
    
    
    
    public boolean matchProvOfficeByDesc(Province p, String label){
            if(p.getRegDesc().equals(label)){
                return true;
            }
        return false;
    }
    
}
