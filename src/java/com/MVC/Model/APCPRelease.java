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
public class APCPRelease {

    private int releaseID;
    private int requestID;
    private double OSBalance;
    private double releaseAmount;
    private Date releaseDate;
    private double currentYearReleaseAmount;
    private double totalAmountReleased;
    private int releasedBy;

    public int getReleaseID() {
        return releaseID;
    }

    public void setReleaseID(int releaseID) {
        this.releaseID = releaseID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public double getOSBalance() {
        return OSBalance;
    }

    public void setOSBalance(double OSBalance) {
        this.OSBalance = OSBalance;
    }
    
    

    public double getReleaseAmount() {
        return releaseAmount;
    }

    public void setReleaseAmount(Double releaseAmount) {
        this.releaseAmount = releaseAmount;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getCurrentYearReleaseAmount() {
        return currentYearReleaseAmount;
    }

    public void setCurrentYearReleaseAmount(double currentReleaseAmount) {
        this.currentYearReleaseAmount = currentReleaseAmount;
    }

    public double getTotalAmountReleased() {
        return totalAmountReleased;
    }

    public void setTotalAmountReleased(double totalAmountReleased) {
        this.totalAmountReleased = totalAmountReleased;
    }

    public int getReleasedBy() {
        return releasedBy;
    }

    public void setReleasedBy(int releasedBy) {
        this.releasedBy = releasedBy;
    }

}
