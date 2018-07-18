/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.CAPDEVDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rey Christian
 */
public class APCPRequest {

    private int requestID;
    private int arboID;
    private int apcpType;
    private String apcpTypeDesc;
    private LoanReason loanReason;
    private int loanTermDuration;
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
    private int loanTrackingNo;
    private int isNewAccessingRequest;
    private int cropProdID;
    private Date dateCompleted;
    private ArrayList<APCPDocument> apcpDocument = new ArrayList();
    private ArrayList<PastDueAccount> pastDueAccounts = new ArrayList();
    private ArrayList<PastDueAccount> unsettledPastDueAccounts = new ArrayList();
    private ArrayList<APCPRelease> releases = new ArrayList();
    private ArrayList<Repayment> arboRepayments = new ArrayList();
    private ArrayList<Repayment> arbRepayments = new ArrayList();
    private ArrayList<ARB> recipients = new ArrayList();
    private ArrayList<CAPDEVPlan> plans = new ArrayList();
    private ArrayList<Disbursement> disbursements = new ArrayList();

    private double totalReleasedAmount;
    private double yearlyReleasedAmount;
    private double totalOSBalance;
    private double totalPastDueAmount;
    private ArrayList<PastDueAccount> pastDueReasons = new ArrayList();
    private Date dateLastRelease;

    public ArrayList<Disbursement> getDisbursements() {
        return disbursements;
    }

    public void setDisbursements(ArrayList<Disbursement> disbursements) {
        this.disbursements = disbursements;
    }

    public double getTotalReleaseOSBalance() {
        double value = 0;
        
        APCPRequestDAO dao = new APCPRequestDAO();
        
        this.releases = dao.getAllAPCPReleasesByRequest(this.requestID);
        this.arboRepayments = dao.getAllARBORepaymentsByRequest(this.requestID);
        
        for (APCPRelease r : this.releases) {
            value += r.getOSBalance();
        }
        for (Repayment p : this.arboRepayments) {
            value -= p.getAmount();
        }
        return value;
    }

    public double getTotalARBOSBalance(int arbID) {
        APCPRequestDAO dao = new APCPRequestDAO();
        ArrayList<Repayment> arbRepayments = dao.getArbRepaymentsByARB(arbID);
        ArrayList<Disbursement> arbDisbursements = dao.getAllDisbursementsByARB(arbID);

        double totDisbursements = 0;
        for (Disbursement d : arbDisbursements) {
            totDisbursements += d.getDisbursedAmount();
        }

        double totRepayments = 0;
        for (Repayment r : arbRepayments) {
            totRepayments += r.getAmount();
        }

        return totDisbursements - totRepayments;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public int getCropProdID() {
        return cropProdID;
    }

    public void setCropProdID(int cropProdID) {
        this.cropProdID = cropProdID;
    }

    public ArrayList<APCPDocument> getApcpDocument() {
        return apcpDocument;
    }

    public void setApcpDocument(ArrayList<APCPDocument> apcpDocument) {
        this.apcpDocument = apcpDocument;
    }

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

    public int getApcpType() {
        return apcpType;
    }

    public void setApcpType(int apcpType) {
        this.apcpType = apcpType;
    }

    public String getApcpTypeDesc() {
        return apcpTypeDesc;
    }

    public void setApcpTypeDesc(String apcpTypeDesc) {
        this.apcpTypeDesc = apcpTypeDesc;
    }

    public LoanReason getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(LoanReason loanReason) {
        this.loanReason = loanReason;
    }

    public int getLoanTermDuration() {
        return loanTermDuration;
    }

    public void setLoanTermDuration(int loanTermDuration) {
        this.loanTermDuration = loanTermDuration;
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

    public int getIsNewAccessingRequest() {
        return isNewAccessingRequest;
    }

    public void setIsNewAccessingRequest(int isNewAccessingRequest) {
        this.isNewAccessingRequest = isNewAccessingRequest;
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

    public ArrayList<ARB> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<ARB> recipients) {
        this.recipients = recipients;
    }

    public ArrayList<CAPDEVPlan> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<CAPDEVPlan> plans) {
        this.plans = plans;
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
        APCPRequestDAO dao = new APCPRequestDAO();
        this.releases = dao.getAllAPCPReleasesByRequest(this.requestID);
        System.out.println("RELEASES:"+this.releases.size());
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

    public ArrayList<Repayment> getArboRepayments() {
        return arboRepayments;
    }

    public void setArboRepayments(ArrayList<Repayment> repayments) {
        this.arboRepayments = repayments;
    }

    public ArrayList<Repayment> getArbRepayments() {
        return arbRepayments;
    }

    public void setArbRepayments(ArrayList<Repayment> repayments) {
        this.arbRepayments = repayments;
    }

    public double getProgressBarWidth(double val1, double val2) {
        return (val1 / val2) * 100;
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

    public Date getDateFirstReleasedPerRequest() {
        if (!releases.isEmpty()) {
            return releases.get(0).getReleaseDate();
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
                if (pda.getReasonPastDue() == this.pastDueReasons.get(this.pastDueReasons.size() - 1).getReasonPastDue()) {
                    sb.append(pda.getReasonPastDueDesc());
                } else {
                    sb.append(pda.getReasonPastDueDesc() + ", ");
                }
            }
        }

        return sb.toString();
    }

    public boolean checkARBOHadAPCPOrientation() {
        CAPDEVDAO dao = new CAPDEVDAO();
        this.plans = dao.getAllCAPDEVPlanByRequest(this.requestID);
        for (CAPDEVPlan plan : this.plans) {
            plan.setActivities(dao.getCAPDEVPlanActivities(plan.getPlanID()));
            for (CAPDEVActivity act : plan.getActivities()) {
                if (plan.getPlanStatus() == 5 && act.getActivityType() == 2) { // IMPLEMENTED and APCP ORIENTATION
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfRequestIsOnTrack(Date date) {

        if (getDateDiff(date) < 5) {
            return true;
        }
        return false;
    }

    public int getDateDiff(Date date1) {
        long diffInMillies = System.currentTimeMillis() - date1.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}
