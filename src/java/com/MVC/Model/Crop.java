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
public class Crop {
    
    private int cropType;
    private String cropTypeDesc;
    private Date startDate;
    private Date endDate;

    public int getCropType() {
        return cropType;
    }

    public void setCropType(int cropType) {
        this.cropType = cropType;
    }

    public String getCropTypeDesc() {
        return cropTypeDesc;
    }

    public void setCropTypeDesc(String cropTypeDesc) {
        this.cropTypeDesc = cropTypeDesc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
