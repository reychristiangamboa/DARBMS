/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
    private ArrayList<PastDueAccount> pastDueAccounts = new ArrayList();
    private ArrayList<PastDueAccount> unsettledPastDueAccounts = new ArrayList();
    private ArrayList<APCPRelease> releases = new ArrayList();
    private ArrayList<Repayment> repayments = new ArrayList();

    private double totalReleasedAmount;
    private double yearlyReleasedAmount;
    private double totalOSBalance;
    private double totalPastDueAmount;
    private ArrayList<PastDueAccount> pastDueReasons = new ArrayList();
    private Date dateLastRelease;

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

    public ArrayList<PastDueAccount> getPastDueAccounts() {
        return pastDueAccounts;
    }

    public void setPastDueAccounts(ArrayList<PastDueAccount> pastDueAccounts) {
        this.pastDueAccounts = pastDueAccounts;
    }

    public ArrayList<PastDueAccount> getUnsettledPastDueAccounts() {
        return unsettledPastDueAccounts;
    }

    public void setUnsettledPastDueAccounts(ArrayList<PastDueAccount> unsettledPastDueAccounts) {
        this.unsettledPastDueAccounts = unsettledPastDueAccounts;
    }
    
    

    public double getTotalPDAAmountPerRequest() {
        double val = 0;
        for (PastDueAccount pda : this.unsettledPastDueAccounts) {
            val += pda.getPastDueAmount();
        }
        return val;
    }

    public ArrayList<APCPRelease> getReleases() {
        return releases;
    }

    public double getTotalReleasedAmountPerRequest() {
        double val = 0;
        for (APCPRelease release : this.releases) {
            val += release.getReleaseAmount();
        }
        return val;
    }

    public double getYearlyReleaseAmountPerRequest() {
        double val = 0;
        Long l = System.currentTimeMillis();
        Date d = new Date(l);

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        int currentYear = 0;
        int releaseYear = 0;

        cal.setTime(d);
        currentYear = cal.get(Calendar.YEAR);

        for (APCPRelease release : this.releases) {
            cal2.setTime(release.getReleaseDate());
            releaseYear = cal2.get(Calendar.YEAR);

            if (currentYear == releaseYear) {
                val += release.getReleaseAmount();
            }

        }
        return val;
    }

    public void setReleases(ArrayList<APCPRelease> releases) {
        this.releases = releases;
    }

    public ArrayList<Repayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(ArrayList<Repayment> repayments) {
        this.repayments = repayments;
    }

    public double getProgressBarWidth(double val1, double val2) {
        return (val1 / val2) * 100;
    }

    public double getAccumulatedOSBalancePerRequest() {
        double value = 0;
        double value2 = 0;
        double value3 = 0;
        
        for (APCPRelease r : this.releases) {
            value += r.getTotalOSBalance();
        }
        for(Repayment rep : this.repayments){
            value2 += rep.getAmount();
        }
        
        value3 = value - value2;
        
        return value;
    }

    public boolean sameARBO(int arboID) {
        if (arboID == this.arboID) {
            return true;
        }
        return false;
    }

    public void setTotalRequestedAmount(double requestedAmount) {
        this.loanAmount += requestedAmount;
    }

    public void setTotalReleaseAmount(double releaseAmount) {
        this.totalReleasedAmount += releaseAmount;
    }

    public void setYearlyReleasedAmount(double yearlyReleasedAmount) {
        this.yearlyReleasedAmount += yearlyReleasedAmount;
    }

    public void setTotalOSBalance(double totalOSBalance) {
        this.totalOSBalance += totalOSBalance;
    }

    public void setTotalPastDueAmount(double totalPastDueAmount) {
        this.totalPastDueAmount += totalPastDueAmount;
    }

    public void setPastDueReasons(ArrayList<PastDueAccount> pastDueReasons) {
        for (PastDueAccount pda : pastDueReasons) {
            if (!this.pastDueReasons.contains(pda) && pda.getActive() == 1) {
                this.pastDueReasons.add(pda);
            }
        }
    }

    public double getTotalReleasedAmount() {
        return totalReleasedAmount;
    }

    public double getYearlyReleasedAmount() {
        return yearlyReleasedAmount;
    }

    public double getTotalOSBalance() {
        return totalOSBalance;
    }

    public double getTotalPastDueAmount() {
        return totalPastDueAmount;
    }

    public ArrayList<PastDueAccount> getPastDueReasons() {
        return pastDueReasons;
    }

    public Date getDateLastRelease() {
        return dateLastRelease;
    }

    public Date getDateLastReleasedPerRequest() {
        if (!releases.isEmpty()) {
            return releases.get(this.getReleases().size() - 1).getReleaseDate();
        }
        return null;
    }

    public void setDateLastRelease(Date dateLastRelease) {
        this.dateLastRelease = dateLastRelease;
    }

    public String printAllPastDueReasons() {
        StringBuilder sb = new StringBuilder();
        if (!this.pastDueReasons.isEmpty()) {
            for (PastDueAccount pda : this.pastDueReasons) {
                if(pda.getReasonPastDue() == this.pastDueReasons.get(this.pastDueReasons.size()-1).getReasonPastDue()){
                    sb.append(pda.getReasonPastDueDesc());
                }else{
                    sb.append(pda.getReasonPastDueDesc() + ", ");
                }
            }
        }

        return sb.toString();
    }
    
    

}
