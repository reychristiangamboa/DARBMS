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
public class Crop {

    private int cropType;
    private String cropTypeDesc;
    private Date startDate;
    private Date endDate;
    private ArrayList<Crop> existingCrops;

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

    public ArrayList<Crop> getExistingCrops(ArrayList<ARB> arbList) {
        for (ARB a : arbList) {
            for (Crop c : a.getCrops()) {
                if (!existingCrops.isEmpty()) {
                    for (Crop c1 : existingCrops) {
                        if (c.getCropType() != c1.getCropType()) {
                            existingCrops.add(c);
                        }
                    }
                } else{
                    existingCrops.add(c);
                }
            }
        }
        return existingCrops;
    }

    public void setExistingCrops(ArrayList<Crop> exisitingCrops) {
        this.existingCrops = exisitingCrops;
    }

}
