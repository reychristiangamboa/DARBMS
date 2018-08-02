/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.*;
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
public class IssueDAO {

    public ArrayList<Issue> retrieveUnresolvedIssues(int issuedTo, int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Issue> issueList = new ArrayList();
        try {
            String query = "SELECT * FROM issues i "
                    + "JOIN ref_issueType r ON i.issueType = r.issueType "
                    + "WHERE i.issuedTo = ? AND i.provOfficeCode = ? AND i.isResolved = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, issuedTo);
            pstmt.setInt(2, provOfficeCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Issue i = new Issue();
                i.setId(rs.getInt("id"));
                i.setIssueType(rs.getInt("issueType"));
                i.setIssueTypeDesc(rs.getString("issueTypeDesc"));
                i.setIssuedTo(rs.getInt("issuedTo"));
                i.setIssuedBy(rs.getInt("issuedBy"));
                i.setProvOfficeCode(rs.getInt("provOfficeCode"));
                i.setRequestID(rs.getInt("requestID"));
                i.setPlanID(rs.getInt("planID"));
                i.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                i.setDateRecorded(rs.getDate("dateRecorded"));
                i.setDateResolved(rs.getDate("dateResolved"));
                i.setFindings(rs.getString("findings"));
                i.setResolution(rs.getString("resolution"));
                i.setResolved(rs.getBoolean("isResolved"));
                issueList.add(i);
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
        return issueList;
    }
    
    public ArrayList<Issue> retrieveResolvedIssues(int issuedTo, int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Issue> issueList = new ArrayList();
        try {
            String query = "SELECT * FROM issues i "
                    + "JOIN ref_issueType r ON i.issueType = r.issueType "
                    + "WHERE i.issuedTo = ? AND i.provOfficeCode = ? AND i.isResolved = 1";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, issuedTo);
            pstmt.setInt(2, provOfficeCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Issue i = new Issue();
                i.setId(rs.getInt("id"));
                i.setIssueType(rs.getInt("issueType"));
                i.setIssueTypeDesc(rs.getString("issueTypeDesc"));
                i.setIssuedTo(rs.getInt("issuedTo"));
                i.setIssuedBy(rs.getInt("issuedBy"));
                i.setProvOfficeCode(rs.getInt("provOfficeCode"));
                i.setRequestID(rs.getInt("requestID"));
                i.setPlanID(rs.getInt("planID"));
                i.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                i.setDateRecorded(rs.getDate("dateRecorded"));
                i.setDateResolved(rs.getDate("dateResolved"));
                i.setFindings(rs.getString("findings"));
                i.setResolution(rs.getString("resolution"));
                i.setResolved(rs.getBoolean("isResolved"));
                issueList.add(i);
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
        return issueList;
    }

    public boolean raiseIssue(Issue i) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();

        Long l = System.currentTimeMillis();
        Date d = new Date(l);

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`issues` (`issueType`, `issuedTo`, `issuedBy`, `provOfficeCode`, `requestID`, `planID`, `pastDueAccountID`, `dateRecorded`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, i.getIssueType());
            p.setInt(2, i.getIssuedTo());
            p.setInt(3, i.getIssuedBy());
            p.setInt(4, i.getProvOfficeCode());
            p.setInt(5, i.getRequestID());
            p.setInt(6, i.getPlanID());
            p.setInt(7, i.getPastDueAccountID());
            p.setDate(8, d);

            p.executeUpdate();
            success = true;
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
        return success;
    }

    public boolean resolveIssue(Issue i) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();

        Long l = System.currentTimeMillis();
        Date d = new Date(l);

        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`issues` SET `dateResolved`=?,`findings`=?, `resolution`=?, `isResolved`=1 WHERE `id`=?;";
            p = con.prepareStatement(query);
            p.setDate(1, d);
            p.setString(2, i.getFindings());
            p.setString(3, i.getResolution());
            p.setInt(4, i.getId());

            p.executeUpdate();
            success = true;
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
        return success;
    }

    public ArrayList<Issue> getAllIssues() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Issue> issueList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_issueType r";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Issue i = new Issue();
                i.setIssueType(rs.getInt("issueType"));
                i.setIssueTypeDesc(rs.getString("issueTypeDesc"));
                issueList.add(i);
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
        return issueList;
    }

    public ArrayList<Issue> getAllIssuesByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Issue> issueList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_issueType r WHERE r.requestID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Issue i = new Issue();
                i.setId(rs.getInt("id"));
                i.setIssueType(rs.getInt("issueType"));
                i.setIssueTypeDesc(rs.getString("issueTypeDesc"));
                i.setRequestID(rs.getInt("requestID"));
                i.setDateRecorded(rs.getDate("dateRecorded"));
                i.setDateResolved(rs.getDate("dateResolved"));
                i.setFindings(rs.getString("findings"));
                i.setResolution(rs.getString("resolution"));
                issueList.add(i);
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
        return issueList;
    }

    public ArrayList<Issue> getAllIssuesByPlan(int planID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Issue> issueList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_issueType r WHERE r.planID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, planID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Issue i = new Issue();
                i.setId(rs.getInt("id"));
                i.setIssueType(rs.getInt("issueType"));
                i.setIssueTypeDesc(rs.getString("issueTypeDesc"));
                i.setPlanID(rs.getInt("planID"));
                i.setDateRecorded(rs.getDate("dateRecorded"));
                i.setDateResolved(rs.getDate("dateResolved"));
                i.setFindings(rs.getString("findings"));
                i.setResolution(rs.getString("resolution"));
                issueList.add(i);
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
        return issueList;
    }

    public ArrayList<Issue> getAllIssuesByPastDueAccount(int pastDueAccountID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Issue> issueList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_issueType r WHERE r.pastDueAccountID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, pastDueAccountID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Issue i = new Issue();
                i.setId(rs.getInt("id"));
                i.setIssueType(rs.getInt("issueType"));
                i.setIssueTypeDesc(rs.getString("issueTypeDesc"));
                i.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                i.setDateRecorded(rs.getDate("dateRecorded"));
                i.setDateResolved(rs.getDate("dateResolved"));
                i.setFindings(rs.getString("findings"));
                i.setResolution(rs.getString("resolution"));
                issueList.add(i);
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
        return issueList;
    }

}
