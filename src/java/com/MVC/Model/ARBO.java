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
public class ARBO {
    
    private int arboID;
    private String arboName;
    private int arboCityMun;
    private String arboCityMunDesc;
    private int arboProvince;
    private String arboProvinceDesc;
    private int arboRegion;
    private String arboRegionDesc;
    private int provOfficeCode;
    private String provOfficeCodeDesc;
    private int APCPQualified;
    private int LinksFarmQualified;

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

    public String getFullAddress(){
        return this.arboProvinceDesc + "," + this.arboRegionDesc;
    }

    public int getAPCPQualified() {
        return APCPQualified;
    }

    public void setAPCPQualified(int APCPQualified) {
        this.APCPQualified = APCPQualified;
    }

    public int getLinksFarmQualified() {
        return LinksFarmQualified;
    }

    public void setLinksFarmQualified(int LinksFarmQualified) {
        this.LinksFarmQualified = LinksFarmQualified;
    }
    
    
    
}
