/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.APCPRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.ARBO;
import java.sql.Date;

/**
 *
 * @author Rey Christian
 */
public class ReportsDAO {

    public ArrayList<APCPRequest> getAllFilteredReleasesByRequests(ArrayList<APCPRequest> requests, Date start, Date end) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        APCPRequestDAO dao = new APCPRequestDAO();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();

        try {
            for (APCPRequest req : requests) {
                ArrayList<APCPRelease> newRelease = new ArrayList();

                String query = "SELECT * FROM request_releases re "
                        + "JOIN apcp_requests request ON re.requestID=request.requestID "
                        + "JOIN ref_requestStatus status ON request.requestStatus=status.requestStatus "
                        + "JOIN arbos a ON request.arboID=a.arboID "
                        + "WHERE (re.releaseDate BETWEEN ? AND ?) AND request.requestID=?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setDate(1, start);
                pstmt.setDate(2, end);
                pstmt.setInt(3, req.getRequestID());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    APCPRelease r = new APCPRelease();
                    r.setReleaseID(rs.getInt("releaseID"));
                    r.setRequestID(rs.getInt("requestID"));
                    r.setReleaseAmount(rs.getDouble("releaseAmount"));
                    r.setReleaseDate(rs.getDate("releaseDate"));
                    r.setReleasedBy(rs.getInt("releasedBy"));
                    r.setDisbursements(dao.getAllDisbursementsByRelease(rs.getInt("releaseID")));
                    newRelease.add(r);
                }
                rs.close();
                pstmt.close();

                req.setReleases(newRelease);
                apcpRequest.add(req);
            }

            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apcpRequest;
    }

    public ArrayList<APCPRequest> getAllAccumulatedARBORequests(ArrayList<ARBO> arboList) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        APCPRequestDAO dao = new APCPRequestDAO();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        ArrayList<APCPRequest> accumulated = new ArrayList();

        try {

            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE a.arboID = ? AND (r.requestStatus =4 OR r.requestStatus =5 OR r.requestStatus =7) ORDER BY r.arboID ";
            for (ARBO arbo : arboList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, arbo.getArboID());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    APCPRequest r = new APCPRequest();
                    r.setRequestID(rs.getInt("requestID"));
                    r.setArboID(rs.getInt("arboID"));
                    r.setDateApproved(rs.getDate("dateApproved"));
                    r.setApprovedBy(rs.getInt("approvedBy"));
                    r.setDateCleared(rs.getDate("dateCleared"));
                    r.setClearedBy(rs.getInt("clearedBy"));
                    r.setDateEndorsed(rs.getDate("dateEndorsed"));
                    r.setEndorsedBy(rs.getInt("endorsedBy"));
                    r.setDateRequested(rs.getDate("dateRequested"));
                    r.setRequestedTo(rs.getInt("requestedTo"));
                    r.setHectares(rs.getDouble("hectares"));
                    r.setLoanAmount(rs.getDouble("loanAmount"));
                    r.setLoanReason(rs.getString("loanReason"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                    r.setRequestStatus(rs.getInt("requestStatus"));
                    r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                    r.setPastDueAccounts(dao.getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setUnsettledPastDueAccounts(dao.getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setReleases(dao.getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                    r.setRepayments(dao.getAllRepaymentsByRequest(rs.getInt("requestID")));
                    apcpRequest.add(r);
                }
                rs.close();
                pstmt.close();
            }
            boolean firstInstance = true;
            APCPRequest request = new APCPRequest();
            System.out.println(apcpRequest.size());
            for (APCPRequest req : apcpRequest) {
                
                if (firstInstance) {
                    request.setArboID(req.getArboID());
                    request.setTotalRequestedAmount(req.getLoanAmount());
                    request.setTotalReleaseAmount(req.getTotalReleasedAmountPerRequest());
                    request.setYearlyReleasedAmount(req.getYearlyReleaseAmountPerRequest());
                    
                    if (req.getDateLastReleasedPerRequest() != null) {
                        request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                    }
                    
                    request.setPastDueReasons(req.getPastDueAccounts());
                    
                    request.setTotalOSBalance(req.getAccumulatedOSBalancePerRequest());
                    request.setTotalPastDueAmount(req.getTotalPDAAmountPerRequest());
                    firstInstance = false;
                } else if (request.getArboID() == req.getArboID()) {
                    request.setTotalRequestedAmount(req.getLoanAmount());
                    request.setTotalReleaseAmount(req.getTotalReleasedAmountPerRequest());
                    request.setYearlyReleasedAmount(req.getYearlyReleaseAmountPerRequest());
                    request.setPastDueReasons(req.getPastDueAccounts());
                    
                    if (req.getDateLastReleasedPerRequest() != null && request.getDateLastReleasedPerRequest() != null) {
                        if (req.getDateLastReleasedPerRequest().after(request.getDateLastReleasedPerRequest())) {
                            request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                        }else{
                            request.setDateLastRelease(request.getDateLastRelease());
                        }
                    }else{
                        request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                    }
                    
                    request.setTotalOSBalance(req.getAccumulatedOSBalancePerRequest());
                    request.setTotalPastDueAmount(req.getTotalPDAAmountPerRequest());
                    firstInstance = false;
                } else {
                    accumulated.add(request);
                    request = new APCPRequest();
                    request.setArboID(req.getArboID());
                    request.setTotalRequestedAmount(req.getLoanAmount());
                    request.setTotalReleaseAmount(req.getTotalReleasedAmountPerRequest());
                    request.setYearlyReleasedAmount(req.getYearlyReleaseAmountPerRequest());
                    request.setPastDueReasons(req.getPastDueAccounts());
                    
                    if (req.getDateLastReleasedPerRequest() != null) {
                        request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                    }
                    
                    request.setTotalOSBalance(req.getAccumulatedOSBalancePerRequest());
                    request.setTotalPastDueAmount(req.getTotalPDAAmountPerRequest());
                    firstInstance = false;
                }
                if(request.getArboID() == apcpRequest.get(apcpRequest.size() -1).getArboID()){
                    accumulated.add(request);
                }
            }
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return accumulated;
    }
    
    public ArrayList<APCPRequest> getAllFilteredAccumulatedARBORequests(ArrayList<ARBO> arboList, Date start, Date end) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        APCPRequestDAO dao = new APCPRequestDAO();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        ArrayList<APCPRequest> accumulated = new ArrayList();

        try {

            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE a.arboID = ? AND (r.requestStatus =4 OR r.requestStatus =5 OR r.requestStatus =7) ORDER BY r.arboID ";
            for (ARBO arbo : arboList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, arbo.getArboID());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    APCPRequest r = new APCPRequest();
                    r.setRequestID(rs.getInt("requestID"));
                    r.setArboID(rs.getInt("arboID"));
                    r.setDateApproved(rs.getDate("dateApproved"));
                    r.setApprovedBy(rs.getInt("approvedBy"));
                    r.setDateCleared(rs.getDate("dateCleared"));
                    r.setClearedBy(rs.getInt("clearedBy"));
                    r.setDateEndorsed(rs.getDate("dateEndorsed"));
                    r.setEndorsedBy(rs.getInt("endorsedBy"));
                    r.setDateRequested(rs.getDate("dateRequested"));
                    r.setRequestedTo(rs.getInt("requestedTo"));
                    r.setHectares(rs.getDouble("hectares"));
                    r.setLoanAmount(rs.getDouble("loanAmount"));
                    r.setLoanReason(rs.getString("loanReason"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                    r.setRequestStatus(rs.getInt("requestStatus"));
                    r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                    
                    r.setPastDueAccounts(dao.getAllFilteredPastDueAccountsByRequest(rs.getInt("requestID"),start,end));
                    r.setUnsettledPastDueAccounts(dao.getAllFilteredUnsettledPastDueAccountsByRequest(rs.getInt("requestID"),start,end));
                    r.setReleases(dao.getAllFilteredAPCPReleasesByRequest(rs.getInt("requestID"),start,end));
                    r.setRepayments(dao.getAllFilteredRepaymentsByRequest(rs.getInt("requestID"),start,end));
                    apcpRequest.add(r);
                }
                rs.close();
                pstmt.close();
            }
            boolean firstInstance = true;
            APCPRequest request = new APCPRequest();
            
            for (APCPRequest req : apcpRequest) {
                
                if (firstInstance) {
                    request.setArboID(req.getArboID());
                    request.setTotalRequestedAmount(req.getLoanAmount());
                    request.setTotalReleaseAmount(req.getTotalReleasedAmountPerRequest());
                    request.setYearlyReleasedAmount(req.getYearlyReleaseAmountPerRequest());
                    
                    if (req.getDateLastReleasedPerRequest() != null) {
                        request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                    }
                    
                    request.setPastDueReasons(req.getPastDueAccounts());
                    
                    request.setTotalOSBalance(req.getAccumulatedOSBalancePerRequest());
                    request.setTotalPastDueAmount(req.getTotalPDAAmountPerRequest());
                    firstInstance = false;
                } else if (request.getArboID() == req.getArboID()) {
                    request.setTotalRequestedAmount(req.getLoanAmount());
                    request.setTotalReleaseAmount(req.getTotalReleasedAmountPerRequest());
                    request.setYearlyReleasedAmount(req.getYearlyReleaseAmountPerRequest());
                    request.setPastDueReasons(req.getPastDueAccounts());
                    
                    if (req.getDateLastReleasedPerRequest() != null && request.getDateLastReleasedPerRequest() != null) {
                        if (req.getDateLastReleasedPerRequest().after(request.getDateLastReleasedPerRequest())) {
                            request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                        }else{
                            request.setDateLastRelease(request.getDateLastRelease());
                        }
                    }else{
                        request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                    }
                    
                    request.setTotalOSBalance(req.getAccumulatedOSBalancePerRequest());
                    request.setTotalPastDueAmount(req.getTotalPDAAmountPerRequest());
                    firstInstance = false;
                } else {
                    accumulated.add(request);
                    request = new APCPRequest();
                    request.setArboID(req.getArboID());
                    request.setTotalRequestedAmount(req.getLoanAmount());
                    request.setTotalReleaseAmount(req.getTotalReleasedAmountPerRequest());
                    request.setYearlyReleasedAmount(req.getYearlyReleaseAmountPerRequest());
                    request.setPastDueReasons(req.getPastDueAccounts());
                    
                    if (req.getDateLastReleasedPerRequest() != null) {
                        request.setDateLastRelease(req.getDateLastReleasedPerRequest());
                    }
                    
                    request.setTotalOSBalance(req.getAccumulatedOSBalancePerRequest());
                    request.setTotalPastDueAmount(req.getTotalPDAAmountPerRequest());
                    firstInstance = false;
                }
                if(request.getArboID() == apcpRequest.get(apcpRequest.size() -1).getArboID()){
                    accumulated.add(request);
                }
            }
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return accumulated;
    }

}
