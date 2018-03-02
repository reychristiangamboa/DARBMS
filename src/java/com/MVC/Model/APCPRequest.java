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
public class APCPRequest {
    private int requestID;
    private int arboID;
    private String loanReason;
    private double loanAmount;
    private double hectares;
    private Date dateRequested;
    private int requestedTo;
    private Date dateCleared;
    private int clearedBy;
    private Date dateEndorsed;
    private int endorsedBy;
    private Date dateApproved;
    private int approvedBy;
    private String remarks;
    private int requestStatus;
    private String requestStatusDesc;
    private Date farmPlanDate;
    private Date businessPlanDate;
    private Date bankRequirementsDate;
    private int loanTrackingNo;

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getArboID() {
        return arboID;
    }

    public void setArboID(int arboID) {
        this.arboID = arboID;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getHectares() {
        return hectares;
    }

    public void setHectares(double hectares) {
        this.hectares = hectares;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Date getDateCleared() {
        return dateCleared;
    }

    public void setDateCleared(Date dateAssessed) {
        this.dateCleared = dateAssessed;
    }

    public int getClearedBy() {
        return clearedBy;
    }

    public void setClearedBy(int clearedBy) {
        this.clearedBy = clearedBy;
    }
    
    public Date getDateEndorsed() {
        return dateEndorsed;
    }

    public void setDateEndorsed(Date dateEndorsed) {
        this.dateEndorsed = dateEndorsed;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestStatusDesc() {
        return requestStatusDesc;
    }

    public void setRequestStatusDesc(String requestStatusDesc) {
        this.requestStatusDesc = requestStatusDesc;
    }

    public int getRequestedTo() {
        return requestedTo;
    }

    public void setRequestedTo(int requestedTo) {
        this.requestedTo = requestedTo;
    }

    public int getEndorsedBy() {
        return endorsedBy;
    }

    public void setEndorsedBy(int endorsedBy) {
        this.endorsedBy = endorsedBy;
    }

    public int getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getFarmPlanDate() {
        return farmPlanDate;
    }

    public void setFarmPlanDate(Date farmPlanDate) {
        this.farmPlanDate = farmPlanDate;
    }

    public Date getBusinessPlanDate() {
        return businessPlanDate;
    }

    public void setBusinessPlanDate(Date businessPlanDate) {
        this.businessPlanDate = businessPlanDate;
    }

    public Date getBankRequirementsDate() {
        return bankRequirementsDate;
    }

    public void setBankRequirementsDate(Date bankRequirementsDate) {
        this.bankRequirementsDate = bankRequirementsDate;
    }

    public int getLoanTrackingNo() {
        return loanTrackingNo;
    }

    public void setLoanTrackingNo(int loanTrackingNo) {
        this.loanTrackingNo = loanTrackingNo;
    }
}
