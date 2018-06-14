/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Rey Christian
 */
public class ARBO {

    private int arboID;
    private String arboName;
    private int arboType;
    private String arboTypeDesc;
    private int arboStatus;
    private String arboStatusDesc;
    private int arboCityMun;
    private String arboCityMunDesc;
    private int arboProvince;
    private String arboProvinceDesc;
    private int arboRegion;
    private String arboRegionDesc;
    private int provOfficeCode;
    private String provOfficeCodeDesc;
    private Date qualifiedSince;
    private Date dateOperational;
    private ArrayList<ARB> arbList = new ArrayList();
    private ArrayList<APCPRequest> requestList = new ArrayList();

    public int getArboID() {
        return arboID;
    }

    public void setArboID(int arboID) {
        this.arboID = arboID;
    }

    public String getArboName() {
        return arboName;
    }

    public void setArboName(String arboName) {
        this.arboName = arboName;
    }

    public int getArboCityMun() {
        return arboCityMun;
    }

    public void setArboCityMun(int arboCityMun) {
        this.arboCityMun = arboCityMun;
    }

    public String getArboCityMunDesc() {
        return arboCityMunDesc;
    }

    public void setArboCityMunDesc(String arboCityMunDesc) {
        this.arboCityMunDesc = arboCityMunDesc;
    }

    public int getArboProvince() {
        return arboProvince;
    }

    public void setArboProvince(int arboProvince) {
        this.arboProvince = arboProvince;
    }

    public String getArboProvinceDesc() {
        return arboProvinceDesc;
    }

    public void setArboProvinceDesc(String arboProvinceDesc) {
        this.arboProvinceDesc = arboProvinceDesc;
    }

    public int getArboRegion() {
        return arboRegion;
    }

    public void setArboRegion(int arboRegion) {
        this.arboRegion = arboRegion;
    }

    public String getArboRegionDesc() {
        return arboRegionDesc;
    }

    public void setArboRegionDesc(String arboRegionDesc) {
        this.arboRegionDesc = arboRegionDesc;
    }

    public int getProvOfficeCode() {
        return provOfficeCode;
    }

    public void setProvOfficeCode(int provOfficeCode) {
        this.provOfficeCode = provOfficeCode;
    }

    public String getProvOfficeCodeDesc() {
        return provOfficeCodeDesc;
    }

    public void setProvOfficeCodeDesc(String provOfficeCodeDesc) {
        this.provOfficeCodeDesc = provOfficeCodeDesc;
    }

    public String getFullAddress() {
        return this.arboCityMunDesc + ", " + this.arboProvinceDesc + "," + this.arboRegionDesc;
    }

    public Date getQualifiedSince() {
        return qualifiedSince;
    }

    public void setQualifiedSince(Date qualifiedSince) {
        this.qualifiedSince = qualifiedSince;
    }

    public int getArboType() {
        return arboType;
    }

    public void setArboType(int arboType) {
        this.arboType = arboType;
    }

    public String getArboTypeDesc() {
        return arboTypeDesc;
    }

    public void setArboTypeDesc(String arboTypeDesc) {
        this.arboTypeDesc = arboTypeDesc;
    }

    public int getArboStatus() {
        return arboStatus;
    }

    public void setArboStatus(int arboStatus) {
        this.arboStatus = arboStatus;
    }

    public String getArboStatusDesc() {
        return arboStatusDesc;
    }

    public void setArboStatusDesc(String arboStatusDesc) {
        this.arboStatusDesc = arboStatusDesc;
    }

    public Date getDateOperational() {
        return dateOperational;
    }

    public void setDateOperational(Date dateOperational) {
        this.dateOperational = dateOperational;
    }

    public ArrayList<ARB> getArbList() {
        return arbList;
    }

    public void setArbList(ArrayList<ARB> arbList) {
        this.arbList = arbList;
    }

    public ArrayList<APCPRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<APCPRequest> requestList) {
        this.requestList = requestList;
    }

    public boolean isSixMonthsOperational() {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(this.dateOperational);
        Calendar endCalendar = new GregorianCalendar();
        
        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        
        if(diffMonth >= 6){
            return true;
        }
        
        return false;
    }

}
