/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.Disbursement;
import com.MVC.Model.PastDueAccount;
import com.MVC.Model.Repayment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class APCPRequestDAO {

    public boolean requestAPCP(APCPRequest r, int userID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`apcp_requests` (`arboID`, `loanReason`, `loanAmount`, "
                    + "`hectares`, `remarks`, `dateRequested`,`requestedTo`,`requestStatus`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, r.getArboID());
            p.setString(2, r.getLoanReason());
            p.setDouble(3, r.getLoanAmount());
            p.setDouble(4, r.getHectares());
            p.setString(5, r.getRemarks());

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(6, d);
            p.setInt(7, userID);

            p.setInt(8, r.getRequestStatus());

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean clearRequest(int requestID, int userID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `dateCleared`=?, `clearedBy`=?, `requestStatus`=2 WHERE `requestID`=?";
            p = con.prepareStatement(query);

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(1, d);
            p.setInt(2, userID);
            p.setInt(3, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean endorseRequest(int requestID, int userID, int ltn) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `dateEndorsed`=?, "
                    + "`endorsedBy`=?, `loanTrackingNo`=?, `requestStatus`=3 WHERE `requestID`=?";
            p = con.prepareStatement(query);

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(1, d);
            p.setInt(2, userID);
            p.setInt(3, ltn);
            p.setInt(4, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean approveRequest(int requestID, int userID, Date date) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `dateApproved`=?, "
                    + "`approvedBy`=?, `requestStatus`=4 WHERE `requestID`=?";
            p = con.prepareStatement(query);

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(1, date);
            p.setInt(2, userID);
            p.setInt(3, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public APCPRequest getRequestByID(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        APCPRequest r = new APCPRequest();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE r.requestID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
                r.setFarmPlanDate(rs.getDate("farmPlanDate"));
                r.setBusinessPlanDate(rs.getDate("businessPlanDate"));
                r.setBankRequirementsDate(rs.getDate("bankRequirementsDate"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public ArrayList<APCPRequest> getAllRequestsByStatus(int statusID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE r.requestStatus = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, statusID);
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
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setLoanReason(rs.getString("loanReason"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllARBORequestsByStatus(int statusID, int arboID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE r.requestStatus = ? AND r.arboID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, statusID);
            pstmt.setInt(2, arboID);
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
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllCityMunRequestsByStatus(int statusID, ArrayList<Integer> cityMunIDs) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            for (int cityMunCode : cityMunIDs) {
                String query = "SELECT * FROM apcp_requests r "
                        + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                        + "JOIN arbos a ON r.arboID=a.arboID "
                        + "WHERE r.requestStatus=? AND a.arboCityMun=?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, statusID);
                pstmt.setInt(2, cityMunCode);
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
                    r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setRequestStatus(rs.getInt("requestStatus"));
                    r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                    r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                    r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                    apcpRequest.add(r);
                }
                rs.close();
                pstmt.close();
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

    public ArrayList<APCPRequest> getAllProvincialRequestsByStatus(int statusID, int provinceID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE r.requestStatus = ? AND a.provOfficeCode = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, statusID);
            pstmt.setInt(2, provinceID);
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
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllProvincialRequestsByStatus(int statusID, ArrayList<Integer> provinceIDs) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {

            for (int provinceID : provinceIDs) {
                String query = "SELECT * FROM apcp_requests r "
                        + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                        + "JOIN arbos a ON r.arboID=a.arboID "
                        + "WHERE r.requestStatus = ? AND a.arboProvince = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, statusID);
                pstmt.setInt(2, provinceID);
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
                    r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setRequestStatus(rs.getInt("requestStatus"));
                    r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                    r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                    r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                    apcpRequest.add(r);
                }
                rs.close();
                pstmt.close();
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

    public ArrayList<APCPRequest> getAllRegionalRequestsByStatus(int statusID, int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "JOIN ref_provoffice p ON a.provOfficeCode=p.provOfficeCode "
                    + "WHERE r.requestStatus = ? AND p.regCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, statusID);
            pstmt.setInt(2, regionID);
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
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllRegionalRequestsByStatus(int statusID, ArrayList<Integer> regionIDs) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            for (int regionID : regionIDs) {
                String query = "SELECT * FROM apcp_requests r "
                        + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                        + "JOIN arbos a ON r.arboID=a.arboID "
                        + "WHERE r.requestStatus = ? AND a.arboRegion = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, statusID);
                pstmt.setInt(2, regionID);
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
                    r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                    r.setRemarks(rs.getString("remarks"));
                    r.setRequestStatus(rs.getInt("requestStatus"));
                    r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                    r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                    r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                    r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                    apcpRequest.add(r);
                }
                rs.close();
                pstmt.close();
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

    public ArrayList<APCPRequest> getAllRequestedRequestsRegion(int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE r.requestStatus <= 3 AND a.arboRegion = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
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
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public boolean sendFarmPlan(Date date, int requestID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `farmPlanDate`=? WHERE `requestID`=?";
            p = con.prepareStatement(query);
            p.setDate(1, date);
            p.setInt(2, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean sendBusinessPlan(Date date, int requestID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `businessPlanDate`=? WHERE `requestID`=?";
            p = con.prepareStatement(query);
            p.setDate(1, date);
            p.setInt(2, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean sendBankRequirements(Date date, int requestID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `bankRequirementsDate`=? WHERE `requestID`=?";
            p = con.prepareStatement(query);
            p.setDate(1, date);
            p.setInt(2, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean updateRequestStatus(int requestID, int statusID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `requestStatus`=? WHERE `requestID`=?";
            p = con.prepareStatement(query);

            p.setInt(1, statusID);
            p.setInt(2, requestID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean addPastDueAccount(PastDueAccount pda) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`past_due_accounts` (`requestID`, `pastDueAmount`, `reasonPastDue`, `otherReason`, `recordedBy`, `dateRecorded`) "
                    + " VALUES (?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, pda.getRequestID());
            p.setDouble(2, pda.getPastDueAmount());
            p.setInt(3, pda.getReasonPastDue());
            p.setString(4, pda.getOtherReason());
            p.setInt(5, pda.getRecordedBy());
            p.setDate(6, pda.getDateRecorded());

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<PastDueAccount> getAllPastDueAccountsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<PastDueAccount> pList = new ArrayList();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue "
                    + "WHERE p.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PastDueAccount p = new PastDueAccount();
                p.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                p.setRequestID(rs.getInt(("requestID")));
                p.setPastDueAmount(rs.getDouble("pastDueAmount"));
                p.setDateSettled(rs.getDate("dateSettled"));
                p.setReasonPastDue(rs.getInt("reasonPastDue"));
                p.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                p.setOtherReason(rs.getString("otherReason"));
                p.setRecordedBy(rs.getInt("recordedBy"));
                p.setActive(rs.getInt("active"));
                pList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pList;
    }

    public PastDueAccount getPastDueAccountByID(int pastDueAccountID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        PastDueAccount p = new PastDueAccount();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue "
                    + "WHERE p.pastDueAccountID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, pastDueAccountID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                p.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                p.setRequestID(rs.getInt(("requestID")));
                p.setPastDueAmount(rs.getDouble("pastDueAmount"));
                p.setDateSettled(rs.getDate("dateSettled"));
                p.setReasonPastDue(rs.getInt("reasonPastDue"));
                p.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                p.setOtherReason(rs.getString("otherReason"));
                p.setRecordedBy(rs.getInt("recordedBy"));
                p.setDateRecorded(rs.getDate("dateRecorded"));
                p.setActive(rs.getInt("active"));

            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public ArrayList<PastDueAccount> getAllFilteredPastDueAccountsByRequest(int requestID, Date start, Date end) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<PastDueAccount> pList = new ArrayList();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue "
                    + "WHERE p.requestID=? AND p.dateRecorded BETWEEN ? AND ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            pstmt.setDate(2, start);
            pstmt.setDate(3, end);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PastDueAccount p = new PastDueAccount();
                p.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                p.setRequestID(rs.getInt(("requestID")));
                p.setPastDueAmount(rs.getDouble("pastDueAmount"));
                p.setDateSettled(rs.getDate("dateSettled"));
                p.setReasonPastDue(rs.getInt("reasonPastDue"));
                p.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                p.setOtherReason(rs.getString("otherReason"));
                p.setRecordedBy(rs.getInt("recordedBy"));
                p.setActive(rs.getInt("active"));
                pList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pList;
    }

    public ArrayList<PastDueAccount> getAllPastDueAccountsByRequestList(ArrayList<APCPRequest> request) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<PastDueAccount> pList = new ArrayList();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue "
                    + "WHERE p.requestID=?";
            for (APCPRequest req : request) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, req.getRequestID());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    PastDueAccount p = new PastDueAccount();
                    p.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                    p.setRequestID(rs.getInt(("requestID")));
                    p.setPastDueAmount(rs.getDouble("pastDueAmount"));
                    p.setDateSettled(rs.getDate("dateSettled"));
                    p.setReasonPastDue(rs.getInt("reasonPastDue"));
                    p.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                    p.setOtherReason(rs.getString("otherReason"));
                    p.setDateRecorded(rs.getDate("dateRecorded"));
                    p.setRecordedBy(rs.getInt("recordedBy"));
                    p.setActive(rs.getInt("active"));
                    pList.add(p);
                }
                rs.close();
                pstmt.close();
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
        return pList;
    }

    public ArrayList<PastDueAccount> getAllUnsettledPastDueAccountsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<PastDueAccount> pList = new ArrayList();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue "
                    + "JOIN apcp_requests req ON p.requestID=req.requestID "
                    + "WHERE p.requestID=? AND p.dateSettled IS NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PastDueAccount p = new PastDueAccount();
                p.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                p.setRequestID(rs.getInt(("requestID")));
                p.setPastDueAmount(rs.getDouble("pastDueAmount"));
                p.setDateSettled(rs.getDate("dateSettled"));
                p.setReasonPastDue(rs.getInt("reasonPastDue"));
                p.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                p.setOtherReason(rs.getString("otherReason"));
                p.setRecordedBy(rs.getInt("recordedBy"));
                p.setActive(rs.getInt("active"));
                pList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pList;
    }

    public ArrayList<PastDueAccount> getAllFilteredUnsettledPastDueAccountsByRequest(int requestID, Date start, Date end) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<PastDueAccount> pList = new ArrayList();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue "
                    + "JOIN apcp_requests req ON p.requestID=req.requestID "
                    + "WHERE p.requestID=? AND p.dateSettled IS NULL AND p.dateRecorded BETWEEN ? AND ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            pstmt.setDate(2, start);
            pstmt.setDate(3, end);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PastDueAccount p = new PastDueAccount();
                p.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                p.setRequestID(rs.getInt(("requestID")));
                p.setPastDueAmount(rs.getDouble("pastDueAmount"));
                p.setDateSettled(rs.getDate("dateSettled"));
                p.setReasonPastDue(rs.getInt("reasonPastDue"));
                p.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                p.setOtherReason(rs.getString("otherReason"));
                p.setRecordedBy(rs.getInt("recordedBy"));
                p.setActive(rs.getInt("active"));
                pList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pList;
    }

    public boolean settlePastDueAccount(PastDueAccount pda) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE past_due_accounts p SET p.dateSettled = ?, p.pastDueAmount=?, p.active=0 WHERE p.pastDueAccountID=?";
            p = con.prepareStatement(query);
            p.setDate(1, pda.getDateSettled());
            p.setDouble(2, pda.getPastDueAmount());
            p.setInt(3, pda.getPastDueAccountID());
            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean addRequestRelease(APCPRelease r) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`request_releases` (`requestID`, `releaseAmount`, `releaseDate`,`releasedBy`) "
                    + " VALUES (?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, r.getRequestID());
            p.setDouble(2, r.getReleaseAmount());
            p.setDate(3, r.getReleaseDate());
            p.setInt(4, r.getReleasedBy());

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean requestHasRelease(APCPRequest r) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "SELECT * request_releases r WHERE r.requestID = ?";
            p = con.prepareStatement(query);
            p.setInt(1, r.getRequestID());

            ResultSet rs = p.executeQuery();
            if (rs.getInt("requestID") > 0) {
                success = true;
            } else {
                success = false;
            }
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<APCPRelease> getAllAPCPReleasesByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRelease> rList = new ArrayList();
        try {
            String query = "SELECT * FROM request_releases r JOIN apcp_requests a ON r.requestID=a.requestID WHERE a.requestID=? ORDER BY r.requestID, r.releaseDate";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                APCPRelease r = new APCPRelease();
                r.setReleaseID(rs.getInt("releaseID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setReleaseAmount(rs.getDouble("releaseAmount"));
                r.setReleaseDate(rs.getDate("releaseDate"));
                r.setReleasedBy(rs.getInt("releasedBy"));
                r.setDisbursements(getAllDisbursementsByRelease(rs.getInt("releaseID")));
                rList.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rList;
    }

    public ArrayList<APCPRelease> getAllFilteredAPCPReleasesByRequest(int requestID, Date start, Date end) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRelease> rList = new ArrayList();
        try {
            String query = "SELECT * FROM request_releases r "
                    + "JOIN apcp_requests a ON r.requestID=a.requestID "
                    + "WHERE a.requestID=? AND r.releaseDate BETWEEN ? AND ? ORDER BY r.requestID, r.releaseDate";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            pstmt.setDate(2, start);
            pstmt.setDate(3, end);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                APCPRelease r = new APCPRelease();
                r.setReleaseID(rs.getInt("releaseID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setReleaseAmount(rs.getDouble("releaseAmount"));
                r.setReleaseDate(rs.getDate("releaseDate"));
                r.setReleasedBy(rs.getInt("releasedBy"));
                r.setDisbursements(getAllDisbursementsByRelease(rs.getInt("releaseID")));
                rList.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rList;
    }

    public APCPRelease getAPCPReleaseByID(int releaseID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        APCPRelease r = new APCPRelease();
        try {
            String query = "SELECT * FROM request_releases r WHERE r.releaseID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, releaseID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                r.setReleaseID(rs.getInt("releaseID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setReleaseAmount(rs.getDouble("releaseAmount"));
                r.setReleaseDate(rs.getDate("releaseDate"));
                r.setReleasedBy(rs.getInt("releasedBy"));
                r.setDisbursements(getAllDisbursementsByRelease(rs.getInt("releaseID")));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public boolean addRepayment(Repayment r) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`repayments` (`requestID`, `amount`,`repaymentDate`,`arbID`,`recordedBy`) "
                    + " VALUES (?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, r.getRequestID());
            p.setDouble(2, r.getAmount());
            p.setDate(3, r.getDateRepayment());
            p.setInt(4, r.getArbID());
            p.setInt(5, r.getRecordedBy());

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<Repayment> getAllRepaymentsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Repayment> rList = new ArrayList();
        try {
            String query = "SELECT * FROM repayments r JOIN apcp_requests a ON r.requestID=a.requestID WHERE r.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setRepaymentID(rs.getInt("repaymentID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setAmount(rs.getDouble("amount"));
                r.setDateRepayment(rs.getDate("repaymentDate"));
                r.setArbID(rs.getInt("arbID"));
                r.setRecordedBy(rs.getInt("recordedBy"));
                rList.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rList;
    }

    public ArrayList<Repayment> getAllFilteredRepaymentsByRequest(int requestID, Date start, Date end) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Repayment> rList = new ArrayList();
        try {
            String query = "SELECT * FROM repayments r "
                    + "JOIN apcp_requests a ON r.requestID=a.requestID "
                    + "WHERE r.requestID=? AND r.repaymentDate BETWEEN ? AND ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            pstmt.setDate(2, start);
            pstmt.setDate(3, end);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setRepaymentID(rs.getInt("repaymentID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setAmount(rs.getDouble("amount"));
                r.setDateRepayment(rs.getDate("repaymentDate"));
                r.setArbID(rs.getInt("arbID"));
                r.setRecordedBy(rs.getInt("recordedBy"));
                rList.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rList;
    }

    public ArrayList<Repayment> getAllRepaymentsByARB(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Repayment> rList = new ArrayList();
        try {
            String query = "SELECT * FROM repayments r JOIN apcp_requests a ON r.requestID=a.requestID WHERE r.arbID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setRepaymentID(rs.getInt("repaymentID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setAmount(rs.getDouble("amount"));
                r.setDateRepayment(rs.getDate("repaymentDate"));
                r.setArbID(rs.getInt("arbID"));
                r.setRecordedBy(rs.getInt("recordedBy"));
                rList.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rList;
    }

    public boolean addDisbursement(Disbursement d) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`disbursements` (`releaseID`, `arbID`, `disbursedAmount`, `OSBalance`,`dateDisbursed`,`disbursedBy`) "
                    + " VALUES (?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, d.getReleaseID());
            p.setInt(2, d.getArbID());
            p.setDouble(3, d.getDisbursedAmount());
            p.setDouble(4, d.getOSBalance());
            p.setDate(5, d.getDateDisbursed());
            p.setInt(6, d.getDisbursedBy());

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<Disbursement> getAllDisbursementsByRelease(int releaseID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Disbursement> dList = new ArrayList();
        try {
            String query = "SELECT * FROM disbursements d JOIN request_releases r ON d.releaseID=r.releaseID WHERE d.releaseID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, releaseID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Disbursement d = new Disbursement();
                d.setDisbursementID(rs.getInt("disbursementID"));
                d.setReleaseID(rs.getInt("releaseID"));
                d.setArbID(rs.getInt("arbID"));
                d.setDisbursedAmount(rs.getDouble("disbursedAmount"));
                d.setOSBalance(rs.getDouble("OSBalance"));
                d.setDateDisbursed(rs.getDate("dateDisbursed"));
                d.setDisbursedBy(rs.getInt("disbursedBy"));
                dList.add(d);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dList;
    }

    public ArrayList<Disbursement> getAllDisbursementsByARB(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Disbursement> dList = new ArrayList();
        try {
            String query = "SELECT * FROM disbursements d "
                    + "WHERE d.arbID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Disbursement d = new Disbursement();
                d.setDisbursementID(rs.getInt("disbursementID"));
                d.setReleaseID(rs.getInt("releaseID"));
                d.setArbID(rs.getInt("arbID"));
                d.setDisbursedAmount(rs.getDouble("disbursedAmount"));
                d.setOSBalance(rs.getDouble("OSBalance"));
                d.setDateDisbursed(rs.getDate("dateDisbursed"));
                d.setDisbursedBy(rs.getInt("disbursedBy"));
                dList.add(d);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dList;
    }

    // FOR REPORTS
    public ArrayList<APCPRequest> getAllProvincialRequests(int provinceID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE a.provOfficeCode = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
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
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllRegionalRequests(int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE a.arboRegion = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
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
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllARBORequests(int arboID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE a.arboID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arboID);
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
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                r.setRepayments(getAllRepaymentsByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllRequestsRegion(int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE a.arboRegion = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
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
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public ArrayList<APCPRequest> getAllRequests() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID";
            PreparedStatement pstmt = con.prepareStatement(query);
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
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setLoanReason(rs.getString("loanReason"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
                apcpRequest.add(r);
            }
            rs.close();
            pstmt.close();
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

    public double getSumOfReleasesByRequestId(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        double sum = 0;
        try {
            String query = "SELECT SUM(releaseAmount) FROM request_releases WHERE requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sum = rs.getDouble("SUM(releaseAmount)");
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sum;
    }

    public double getYearlySumOfReleasesByRequestId(ArrayList<APCPRequest> apcpRequestList, int year) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        double sum = 0;
        try {
            String query = "SELECT SUM(releaseAmount) FROM request_releases WHERE requestID=? AND YEAR(releaseDate)=?";
            for (APCPRequest r : apcpRequestList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, r.getRequestID());
                pstmt.setInt(2, year);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    sum += rs.getDouble("SUM(releaseAmount)");
                }
                rs.close();
                pstmt.close();
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
        return sum;
    }

    public double getSumOfAccumulatedReleasesByRequestId(ArrayList<APCPRequest> apcpRequestList) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        double sum = 0;
        try {
            String query = "SELECT SUM(releaseAmount) FROM request_releases WHERE requestID=?";
            for (APCPRequest r : apcpRequestList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, r.getRequestID());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    sum += rs.getDouble("SUM(releaseAmount)");
                }
                rs.close();
                pstmt.close();
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
        return sum;
    }

    public double getTotalApprovedAmount(ArrayList<APCPRequest> apcpRequestList) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        double sum = 0;
        try {
            String query = "SELECT SUM(loanAmount) FROM apcp_requests WHERE requestID=? AND (requestStatus=4 OR requestStatus=5 OR requestStatus=7)";
            for (APCPRequest r : apcpRequestList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, r.getRequestID());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    sum += rs.getDouble("SUM(loanAmount)");
                }
                rs.close();
                pstmt.close();
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
        return sum;
    }

    public double getTotalPastDueAmount(ArrayList<APCPRequest> apcpRequestList) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        double sum = 0;
        try {
            String query = "SELECT SUM(pastDueAmount) FROM past_due_accounts WHERE requestID=? AND dateSettled IS NULL";
            for (APCPRequest r : apcpRequestList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, r.getRequestID());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    sum += rs.getDouble("SUM(pastDueAmount)");
                }
                rs.close();
                pstmt.close();
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
        return sum;
    }

    public ArrayList<Repayment> getRepaymentHistoryByARBO(int arboID) {

        ArrayList<Repayment> repayment = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.apcp_requests a "
                    + "JOIN repayments r ON a.requestID=r.requestID "
                    + "WHERE a.arboID=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, arboID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setAmount(rs.getInt("amount"));
                r.setArbID(rs.getInt("arbID"));
                r.setRepaymentID(rs.getInt("repaymentID"));
                r.setRecordedBy(rs.getInt("recordedBy"));
                r.setRequestID(rs.getInt("requestID"));
                r.setDateRepayment(rs.getDate("repaymentDate"));
                repayment.add(r);
            }
            rs.close();
            p.close();
            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repayment;
    }

    public boolean checkIfLTNExists(int ltn) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        boolean success = false;

        try {
            String query = "SELECT * FROM apcp_requests r";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("loanTrackingNo") == ltn) {
                    success = true;
                }
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(APCPRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

}
