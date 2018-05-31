/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.*;
import java.sql.Connection;
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
