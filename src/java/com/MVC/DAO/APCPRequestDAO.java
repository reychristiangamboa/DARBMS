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
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import be.ceau.chart.BarChart;
import be.ceau.chart.LineChart;
import be.ceau.chart.PieChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.data.LineData;
import be.ceau.chart.data.PieData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.dataset.PieDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.*;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.AddressDAO;
import com.MVC.DAO.CAPDEVDAO;
import java.util.ArrayList;
import com.MVC.DAO.CropDAO;
import com.MVC.Model.Crop;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rey Christian
 */
public class APCPRequestDAO {

    public ArrayList<APCPRequest> getAllRequests() {
        System.out.println("LOL!! TINATAWAGAN SI GET ALL!!");
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        CAPDEVDAO dao = new CAPDEVDAO();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN ref_apcpType t ON r.apcpType=t.apcpType "
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
                r.setApcpType(rs.getInt("apcpType"));
                r.setApcpTypeDesc(rs.getString("apcpTypeDesc"));
                r.setLoanReason(getAPCPLoanReasonByRequest(rs.getInt("requestID")));
                r.setLoanTermDuration(rs.getInt("loanTermDuration"));
                r.setRemarks(rs.getString("remarks"));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setIsNewAccessingRequest(rs.getInt("isNewAccessingRequest"));
                r.setCropProdID(rs.getInt("cropProdID"));
                r.setDateCompleted(rs.getDate("dateCompleted"));

//                r.setApcpDocument(getAllAPCPDocumentsByRequest(rs.getInt("requestID")));
//                r.setRecipients(getAllAPCPRecipientsByRequest(rs.getInt("requestID")));
//
//                r.setPlans(dao.getAllCAPDEVPlanByRequest(rs.getInt("requestID")));
//
//                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
//                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
//
//                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
//                r.setDisbursements(getAllDisbursementsByRequest(rs.getInt("requestID")));
//                r.setArboRepayments(getAllARBORepaymentsByRequest(rs.getInt("requestID")));
//                r.setArbRepayments(getAllARBRepaymentsByRequest(rs.getInt("requestID")));
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

    public ArrayList<APCPRequest> getAllCropProdByRequest(int arboID) {
        ArrayList<APCPRequest> all = getAllRequests();
        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : all) {
            if (r.getArboID() == arboID && r.getApcpType() == 1) { // is REQUEST of ARBO and its TYPE is CROP PROD
                filtered.add(r);
            }
        }

        return filtered;
    }

    public APCPRequest getRequestByID(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        CAPDEVDAO dao = new CAPDEVDAO();
        APCPRequest r = new APCPRequest();
        try {
            String query = "SELECT * FROM apcp_requests r "
                    + "JOIN ref_requestStatus s ON r.requestStatus=s.requestStatus "
                    + "JOIN ref_apcpType t ON r.apcpType=t.apcpType "
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
                r.setApcpType(rs.getInt("apcpType"));
                r.setApcpTypeDesc(rs.getString("apcpTypeDesc"));
                r.setLoanReason(getAPCPLoanReasonByRequest(rs.getInt("requestID")));
                r.setLoanTermDuration(rs.getInt("loanTermDuration"));
                r.setRemarks(rs.getString("remarks"));
                r.setApcpDocument(getAllAPCPDocumentsByRequest(rs.getInt("requestID")));
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
                r.setLoanTrackingNo(rs.getInt("loanTrackingNo"));
                r.setDateCompleted(rs.getDate("dateCompleted"));
                r.setIsNewAccessingRequest(rs.getInt("isNewAccessingRequest"));
//                r.setPastDueAccounts(getAllPastDueAccountsByRequest(rs.getInt("requestID")));
//                r.setUnsettledPastDueAccounts(getAllUnsettledPastDueAccountsByRequest(rs.getInt("requestID")));
//                r.setReleases(getAllAPCPReleasesByRequest(rs.getInt("requestID")));
//                r.setRecipients(getAllAPCPRecipientsByRequest(rs.getInt("requestID")));
//                r.setPlans(dao.getAllCAPDEVPlanByRequest(rs.getInt("requestID")));
//                r.setDisbursements(getAllDisbursementsByRequest(rs.getInt("requestID")));
//                r.setArboRepayments(getAllARBORepaymentsByRequest(rs.getInt("requestID")));
//                r.setArbRepayments(getAllARBRepaymentsByRequest(rs.getInt("requestID")));
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

    public ArrayList<APCPRequest> getFilteredByStatus(ArrayList<APCPRequest> filtered, int status) {
        ArrayList<APCPRequest> filteredReq = new ArrayList();

        for (APCPRequest req : filtered) {
            if (req.getRequestStatus() == status) {
                filteredReq.add(req);
            }
        }

        return filteredReq;
    }

    public int requestAPCPLivelihood(APCPRequest r, int userID) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`apcp_requests` (`arboID`, `loanAmount`, "
                    + "`hectares`, `remarks`, `dateRequested`,`requestedTo`,`requestStatus`,`apcpType`,`cropProdID`,`loanTermDuration`,`isNewAccessingRequest`, `dateCompleted`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, r.getArboID());
            p.setDouble(2, r.getLoanAmount());
            p.setDouble(3, r.getHectares());
            p.setString(4, r.getRemarks());

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(5, d);
            p.setInt(6, userID);
            p.setInt(7, r.getRequestStatus());
            p.setInt(8, r.getApcpType());
            p.setInt(9, r.getCropProdID());
            p.setInt(10, r.getLoanTermDuration());
            p.setInt(11, 0);
            p.setDate(12, r.getDateCompleted());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

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
        return 0;
    }

    public int requestAPCPCropProd(APCPRequest r, int userID) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`apcp_requests` (`arboID`, `loanAmount`, "
                    + "`hectares`, `remarks`, `dateRequested`,`requestedTo`,`requestStatus`,`apcpType`,`loanTermDuration`,`isNewAccessingRequest`, `dateCompleted`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, r.getArboID());
            p.setDouble(2, r.getLoanAmount());
            p.setDouble(3, r.getHectares());
            p.setString(4, r.getRemarks());

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(5, d);
            p.setInt(6, userID);
            p.setInt(7, r.getRequestStatus());
            p.setInt(8, r.getApcpType());
            p.setInt(9, r.getLoanTermDuration());
            p.setInt(10, 0);
            p.setDate(11, r.getDateCompleted());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

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
        return 0;
    }

    public int requestAPCPWithIssue(APCPRequest r, int userID) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`apcp_requests` (`arboID`, `loanAmount`, "
                    + "`hectares`, `remarks`, `dateRequested`,`requestedTo`,`requestStatus`,`apcpType`,`loanTermDuration`,`isNewAccessingRequest`, `dateCompleted`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, r.getArboID());
            p.setDouble(2, r.getLoanAmount());
            p.setDouble(3, r.getHectares());
            p.setString(4, r.getRemarks());

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(5, d);
            p.setInt(6, userID);
            p.setInt(7, 11);
            p.setInt(8, r.getApcpType());
            p.setInt(9, r.getLoanTermDuration());
            p.setInt(10, 0);
            p.setDate(11, r.getDateCompleted());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

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
        return 0;
    }

    public int requestAPCPNewAccess(APCPRequest r, int userID) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`apcp_requests` (`arboID`, `loanAmount`, "
                    + "`hectares`, `remarks`, `dateRequested`,`requestedTo`,`requestStatus`,`apcpType`,`loanTermDuration`,`isNewAccessingRequest`) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, r.getArboID());
            p.setDouble(2, r.getLoanAmount());
            p.setDouble(3, r.getHectares());
            p.setString(4, r.getRemarks());

            Long l = System.currentTimeMillis();
            Date d = new Date(l);

            p.setDate(5, d);
            p.setInt(6, userID);
            p.setInt(7, r.getRequestStatus());
            p.setInt(8, r.getApcpType());
            p.setInt(9, r.getLoanTermDuration());
            p.setInt(10, 1);

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

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
        return 0;
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

    public ArrayList<APCPRequest> getAllRequestsByStatus(int statusID) {
        ArrayList<APCPRequest> all = getAllRequests();
        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : all) {
            if (r.getRequestStatus() == statusID) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public ArrayList<APCPRequest> getAllAPCPTypes() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM ref_apcpType";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                APCPRequest r = new APCPRequest();
                r.setApcpType(rs.getInt("apcpType"));
                r.setApcpTypeDesc(rs.getString("apcpTypeDesc"));
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
        ArrayList<APCPRequest> all = getAllRequests();
        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : all) {
            if (r.getRequestStatus() == statusID && r.getArboID() == arboID) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public ArrayList<APCPRequest> getAllProvincialRequestsByStatus(int statusID, int provinceID) {
        ARBODAO dao = new ARBODAO();
        ArrayList<APCPRequest> all = getAllRequests();
        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : all) {
            ARBO a = dao.getARBOByID(r.getArboID());
            if (r.getRequestStatus() == statusID && a.getProvOfficeCode() == provinceID) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public ArrayList<APCPRequest> getAllProvincialRequestsByStatus(int statusID, ArrayList<Integer> provinceIDs) {
        ArrayList<APCPRequest> apcpRequestList = getAllRequestsByStatus(statusID);
        ArrayList<APCPRequest> list = new ArrayList();
        ARBODAO dao = new ARBODAO();
        for (APCPRequest r : apcpRequestList) {
            for (int id : provinceIDs) {
                ARBO arbo = dao.getARBOByID(r.getArboID());
                if (arbo.getProvOfficeCode() == id) {
                    list.add(r);
                }
            }
        }
        return list;
    }

    public ArrayList<APCPRequest> getAllRegionalRequestsByStatus(int statusID, int regionID) {
        ARBODAO dao = new ARBODAO();
        ArrayList<APCPRequest> all = getAllRequests();
        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : all) {
            ARBO a = dao.getARBOByID(r.getArboID());
            if (r.getRequestStatus() == statusID && a.getArboRegion() == regionID) {
                System.out.println("TRUE NAMAN!!");
                filtered.add(r);
            }
        }

        return filtered;
    }

    public ArrayList<APCPRequest> getAllRegionalRequestsByStatus(int statusID, ArrayList<Integer> regionIDs) {
        ArrayList<APCPRequest> apcpRequestList = getAllRequestsByStatus(statusID);
        ArrayList<APCPRequest> list = new ArrayList();
        ARBODAO dao = new ARBODAO();
        for (APCPRequest r : apcpRequestList) {
            for (int id : regionIDs) {
                ARBO arbo = dao.getARBOByID(r.getArboID());
                if (arbo.getArboRegion() == id) {
                    list.add(r);
                }
            }
        }
        return list;
    }

    public ArrayList<APCPRequest> getAllRequestStatus() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPRequest> apcpRequest = new ArrayList();
        try {
            String query = "SELECT * FROM ref_requestStatus";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                APCPRequest r = new APCPRequest();
                r.setRequestStatus(rs.getInt("requestStatus"));
                r.setRequestStatusDesc(rs.getString("requestStatusDesc"));
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

    public boolean addAPCPRecipient(int requestID, int arbID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO apcp_recipients(arbID, requestID) VALUES (?,?)";
            p = con.prepareStatement(query);
            p.setInt(1, arbID);
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

    public boolean removeAPCPRecipient(int requestID, int arbID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "DELETE FROM `dar-bms`.`apcp_recipients` WHERE `arbID`=? AND `requestID`=?;";
            p = con.prepareStatement(query);
            p.setInt(1, arbID);
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

    public void setAPCPLoanReason(int requestID, int loanReason, int loanTerm, String otherReason) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO apcp_loanReason (requestID,loanReason,loanTerm,otherReason) "
                    + "VALUES (?,?,?,?)";
            p = con.prepareStatement(query);
            p.setInt(1, requestID);
            p.setInt(2, loanReason);
            p.setInt(3, loanTerm);
            p.setString(4, otherReason);

            p.executeUpdate();

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

    }

    public boolean sendDocument(APCPDocument doc, int requestID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO apcp_documents (requestID,document,documentName,dateSubmitted,isApproved) "
                    + "VALUES (?,?,?,?,?)";
            p = con.prepareStatement(query);
            p.setInt(1, requestID);
            p.setInt(2, doc.getDocument());
            p.setString(3, doc.getDocumentName());
            p.setDate(4, doc.getDateSubmitted());
            p.setBoolean(5, doc.isIsApproved());

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

    public boolean resubmitDocument(APCPDocument doc, int requestID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_documents SET dateSubmitted=? WHERE requestID=? AND document=?";
            p = con.prepareStatement(query);
            p.setDate(1, doc.getDateSubmitted());
            p.setInt(2, requestID);
            p.setInt(3, doc.getDocument());

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

    public boolean approveDocument(int document) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_documents d SET isApproved=1 WHERE d.id = ?";
            p = con.prepareStatement(query);
            p.setInt(1, document);

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

    public void setDateCompleted(int requestID) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();

        Long l = System.currentTimeMillis();
        Date d = new Date(l);

        try {
            con.setAutoCommit(false);
            String query = "UPDATE apcp_requests SET `dateCompleted`=? WHERE `requestID`=?";
            p = con.prepareStatement(query);

            p.setDate(1, d);
            p.setInt(2, requestID);

            p.executeUpdate();
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

    }

    public int addPastDueAccount(PastDueAccount pda) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`past_due_accounts` (`requestID`, `pastDueAmount`, `reasonPastDue`, `otherReason`, `recordedBy`, `dateRecorded`) "
                    + " VALUES (?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, pda.getRequestID());
            p.setDouble(2, pda.getPastDueAmount());
            p.setInt(3, pda.getReasonPastDue());
            p.setString(4, pda.getOtherReason());
            p.setInt(5, pda.getRecordedBy());
            p.setDate(6, pda.getDateRecorded());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

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
        return 0;
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
                p.setDateRecorded(rs.getDate("dateRecorded"));
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
        ArrayList<PastDueAccount> list = getAllPastDueAccounts();
        ArrayList<PastDueAccount> list2 = new ArrayList();
        for (PastDueAccount p : list) {
            for (APCPRequest r : request) {
                if (p.getRequestID() == r.getRequestID()) {
                    list2.add(p);
                }
            }
        }
        return list2;
    }

    public ArrayList<PastDueAccount> getYearlyPastDueAccountsByRequestList(ArrayList<APCPRequest> request, int year) {
        Calendar cal = Calendar.getInstance();
        ArrayList<PastDueAccount> list = getAllPastDueAccounts();
        ArrayList<PastDueAccount> list2 = new ArrayList();
        for (PastDueAccount p : list) {
            for (APCPRequest r : request) {
                cal.setTime(p.getDateRecorded());
                if (p.getRequestID() == r.getRequestID() && cal.get(Calendar.YEAR) == year) {
                    list2.add(p);
                }
            }
        }
        return list2;
    }

    public ArrayList<PastDueAccount> getYearlyUnsettledPastDueAccountsByRequestList(ArrayList<APCPRequest> request, int year) {
        Calendar cal = Calendar.getInstance();
        ArrayList<PastDueAccount> list = getAllPastDueAccounts();
        ArrayList<PastDueAccount> list2 = new ArrayList();
        for (PastDueAccount p : list) {
            for (APCPRequest r : request) {
                cal.setTime(p.getDateRecorded());
                if (p.getRequestID() == r.getRequestID() && cal.get(Calendar.YEAR) == year && p.getDateSettled() == null) {
                    list2.add(p);
                }
            }
        }
        return list2;
    }

    public ArrayList<PastDueAccount> getAllPastDueAccounts() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<PastDueAccount> pList = new ArrayList();
        try {
            String query = "SELECT * FROM past_due_accounts p "
                    + "JOIN ref_reasonPastDue r ON p.reasonPastDue=r.reasonPastDue WHERE p.active = 1 ";
            PreparedStatement pstmt = con.prepareStatement(query);
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
                p.setDateRecorded(rs.getDate("dateRecorded"));
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

    public ArrayList<ARB> getAllAPCPRecipientsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARB> list = new ArrayList();
        ARBDAO dao = new ARBDAO();
        try {
            String query = "SELECT * FROM apcp_recipients a WHERE a.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARB arb = new ARB();
                arb = dao.getARBByID(rs.getInt("arbID"));
                list.add(arb);
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
        return list;
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
            String query = "INSERT INTO `dar-bms`.`request_releases` (`requestID`, `releaseAmount`, `releaseDate`,`releasedBy`,`OSBalance`) "
                    + " VALUES (?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, r.getRequestID());
            p.setDouble(2, r.getReleaseAmount());
            p.setDate(3, r.getReleaseDate());
            p.setInt(4, r.getReleasedBy());
            p.setDouble(5, r.getOSBalance());

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
            String query = "SELECT * FROM request_releases r WHERE r.requestID = ?";
            p = con.prepareStatement(query);
            p.setInt(1, r.getRequestID());

            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                success = true;
            } else {
                success = false;
            }
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
                r.setOSBalance(rs.getDouble("OSBalance"));
                r.setReleaseAmount(rs.getDouble("releaseAmount"));
                r.setReleaseDate(rs.getDate("releaseDate"));
                r.setReleasedBy(rs.getInt("releasedBy"));
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
                r.setOSBalance(rs.getDouble("OSBalance"));
                r.setReleaseAmount(rs.getDouble("releaseAmount"));
                r.setReleaseDate(rs.getDate("releaseDate"));
                r.setReleasedBy(rs.getInt("releasedBy"));
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

    public boolean addARBORepayment(Repayment r) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`arbo_repayments` (`requestID`, `amount`,`repaymentDate`,`recordedBy`) "
                    + " VALUES (?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setInt(1, r.getRequestID());
            p.setDouble(2, r.getAmount());
            p.setDate(3, r.getDateRepayment());
            p.setInt(4, r.getRecordedBy());

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

    public boolean addARBRepayment(Repayment r) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`arb_repayments` (`requestID`, `amount`,`repaymentDate`,`arbID`,`recordedBy`) "
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

    public ArrayList<Repayment> getAllARBORepaymentsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Repayment> rList = new ArrayList();
        try {
            String query = "SELECT * FROM arbo_repayments r JOIN apcp_requests a ON r.requestID=a.requestID WHERE r.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setRepaymentID(rs.getInt("repaymentID"));
                r.setRequestID(rs.getInt("requestID"));
                r.setAmount(rs.getDouble("amount"));
                r.setDateRepayment(rs.getDate("repaymentDate"));
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

    public ArrayList<Repayment> getAllARBRepaymentsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Repayment> rList = new ArrayList();
        try {
            String query = "SELECT * FROM arb_repayments r JOIN apcp_requests a ON r.requestID=a.requestID WHERE r.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setRepaymentID(rs.getInt("arbRepaymentID"));
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
            String query = "SELECT * FROM arbo_repayments r "
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

    public ArrayList<Repayment> getArbRepaymentsByARB(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Repayment> rList = new ArrayList();
        try {
            String query = "SELECT * FROM arb_repayments r JOIN apcp_requests a ON r.requestID=a.requestID WHERE r.arbID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setRepaymentID(rs.getInt("arbRepaymentID"));
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

    public int addDisbursement(Disbursement d) {

        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`disbursements` (`requestID`, `arbID`, `disbursedAmount`, `OSBalance`,`dateDisbursed`,`disbursedBy`,`totalReleasedAmount`) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, d.getRequestID());
            p.setInt(2, d.getArbID());
            p.setDouble(3, d.getDisbursedAmount());
            p.setDouble(4, d.getOSBalance());
            p.setDate(5, d.getDateDisbursed());
            p.setInt(6, d.getDisbursedBy());
            p.setDouble(7, d.getTotalReleasedAmount());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();
            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

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
        return 0;
    }

    public Disbursement getDisbursementsByID(int id) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        Disbursement d = new Disbursement();
        try {
            String query = "SELECT * FROM disbursements d "
                    + "WHERE d.disbursementID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {

                d.setDisbursementID(rs.getInt("disbursementID"));
                d.setRequestID(rs.getInt("requestID"));
                d.setArbID(rs.getInt("arbID"));
                d.setDisbursedAmount(rs.getDouble("disbursedAmount"));
                d.setOSBalance(rs.getDouble("OSBalance"));
                d.setDateDisbursed(rs.getDate("dateDisbursed"));
                d.setDisbursedBy(rs.getInt("disbursedBy"));
                d.setTotalReleasedAmount(rs.getDouble("totalReleasedAmount"));

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
        return d;
    }

    public ArrayList<Disbursement> getAllDisbursementsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Disbursement> dList = new ArrayList();
        try {
            String query = "SELECT * FROM disbursements d JOIN apcp_requests r ON d.requestID=r.requestID WHERE d.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Disbursement d = new Disbursement();
                d.setDisbursementID(rs.getInt("disbursementID"));
                d.setRequestID(rs.getInt("requestID"));
                d.setArbID(rs.getInt("arbID"));
                d.setDisbursedAmount(rs.getDouble("disbursedAmount"));
                d.setOSBalance(rs.getDouble("OSBalance"));
                d.setDateDisbursed(rs.getDate("dateDisbursed"));
                d.setDisbursedBy(rs.getInt("disbursedBy"));
                d.setTotalReleasedAmount(rs.getDouble("totalReleasedAmount"));
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
                d.setRequestID(rs.getInt("requestID"));
                d.setArbID(rs.getInt("arbID"));
                d.setDisbursedAmount(rs.getDouble("disbursedAmount"));
                d.setOSBalance(rs.getDouble("OSBalance"));
                d.setDateDisbursed(rs.getDate("dateDisbursed"));
                d.setDisbursedBy(rs.getInt("disbursedBy"));
                d.setTotalReleasedAmount(rs.getDouble("totalReleasedAmount"));
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

    public ArrayList<Disbursement> getAllDisbursementsByARBPerRequest(int arbID, int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Disbursement> dList = new ArrayList();
        try {
            String query = "SELECT * FROM disbursements d "
                    + "WHERE d.arbID=? AND d.requestID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            pstmt.setInt(2, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Disbursement d = new Disbursement();
                d.setDisbursementID(rs.getInt("disbursementID"));
                d.setRequestID(rs.getInt("requestID"));
                d.setArbID(rs.getInt("arbID"));
                d.setDisbursedAmount(rs.getDouble("disbursedAmount"));
                d.setOSBalance(rs.getDouble("OSBalance"));
                d.setDateDisbursed(rs.getDate("dateDisbursed"));
                d.setDisbursedBy(rs.getInt("disbursedBy"));
                d.setTotalReleasedAmount(rs.getDouble("totalReleasedAmount"));
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

    public ArrayList<APCPDocument> getAllAPCPDocuments() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPDocument> dList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_document d "
                    + "JOIN ref_documentType t ON d.documentType=t.documentType";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                APCPDocument d = new APCPDocument();
                d.setDocument(rs.getInt("document"));
                d.setDocumentDesc(rs.getString("documentDesc"));
                d.setDocumentType(rs.getInt("documentType"));
                d.setDocumentTypeDesc(rs.getString("documentTypeDesc"));
                d.setIsRequired(rs.getInt("isRequired"));
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
    } // REFERENCE

    public ArrayList<APCPDocument> getAllAPCPDocumentsByRequest(int requestID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<APCPDocument> dList = new ArrayList();
        try {
            String query = "SELECT * FROM apcp_documents d "
                    + "JOIN ref_document r ON d.document=r.document "
                    + "JOIN ref_documentType t ON r.documentType=t.documentType "
                    + "WHERE d.requestID = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                APCPDocument d = new APCPDocument();
                d.setId(rs.getInt("id"));
                d.setDocument(rs.getInt("document"));
                d.setDocumentDesc(rs.getString("documentDesc"));
                d.setDocumentName(rs.getString("documentName"));
                d.setDocumentType(rs.getInt("documentType"));
                d.setDocumentTypeDesc(rs.getString("documentTypeDesc"));
                d.setDateSubmitted(rs.getDate("dateSubmitted"));
                d.setIsApproved(rs.getBoolean("isApproved"));
                d.setIsRequired(rs.getInt("isRequired"));
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

    public APCPDocument getAPCPDocumentByDocumentID(int documentID) {
        ArrayList<APCPDocument> allDocuments = getAllAPCPDocuments();
        APCPDocument doc2 = new APCPDocument();
        for (APCPDocument doc : allDocuments) {
            if (doc.getDocument() == documentID) {
                doc2 = doc;
            }
        }
        return doc2;
    }

    public ArrayList<LoanReason> getAllLoanReasons() { // includes Others; REFERENCE

        ArrayList<LoanReason> list = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_loanReason r";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                LoanReason r = new LoanReason();
                r.setLoanReason(rs.getInt("loanReason"));
                r.setLoanReasonDesc(rs.getString("loanReasonDesc"));
                list.add(r);
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
        return list;
    } // REFERENCE

    public ArrayList<LoanReason> getAllLoanReasonsByAPCPType(int apcpType) { // includes Others; REFERENCE

        ArrayList<LoanReason> list = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_loanReason r "
                    + "LEFT JOIN `dar-bms`.ref_apcpType a ON r.apcpType=a.apcpType "
                    + "WHERE r.apcpType=? OR r.apcpType IS NULL ORDER BY r.loanReasonDesc";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, apcpType);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                LoanReason r = new LoanReason();
                r.setLoanReason(rs.getInt("loanReason"));
                r.setLoanReasonDesc(rs.getString("loanReasonDesc"));
                list.add(r);
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
        return list;
    } // REFERENCE

    public ArrayList<LoanTerm> getAllLoanTerms() {
        ArrayList<LoanTerm> all = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_loanTerms t";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                LoanTerm t = new LoanTerm();
                t.setLoanTerm(rs.getInt("loanTerm"));
                t.setLoanTermDesc(rs.getString("loanTermDesc"));
                t.setArboInterestRate(rs.getDouble("arboInterestRate"));
                t.setArbInterestRate(rs.getDouble("arbInterestRate"));
                t.setPastDueInterestRate(rs.getDouble("pastDueInterestRate"));
                t.setMinDuration(rs.getInt("minDuration"));
                t.setMaxDuration(rs.getInt("maxDuration"));
                all.add(t);
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
        return all;
    } // REFERENCE

    public LoanReason getAPCPLoanReasonByRequest(int requestID) {

        LoanReason r = new LoanReason();
        LoanTerm t = new LoanTerm();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM apcp_loanReason r "
                    + "JOIN ref_loanReason lr ON r.loanReason=lr.loanReason "
                    + "JOIN ref_loanTerms lt ON r.loanTerm=lt.loanTerm "
                    + "WHERE r.requestID=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, requestID);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                t.setLoanTerm(rs.getInt("loanTerm"));
                t.setLoanTermDesc(rs.getString("loanTermDesc"));
                t.setArboInterestRate(rs.getDouble("arboInterestRate"));
                t.setArbInterestRate(rs.getDouble("arbInterestRate"));
                t.setPastDueInterestRate(rs.getDouble("pastDueInterestRate"));
                t.setMinDuration(rs.getInt("minDuration"));
                t.setMaxDuration(rs.getInt("maxDuration"));

                r.setLoanTerm(t);
                r.setLoanReason(rs.getInt("loanReason"));
                r.setLoanReasonDesc(rs.getString("loanReasonDesc"));
                r.setOtherReason(rs.getString("otherReason"));
                r.setLoanTermID(rs.getInt("loanTerm"));
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
        return r;
    }

    public LoanReason getLoanReasonById(int loanReason) {

        LoanReason r = new LoanReason();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM ref_loanReason WHERE loanReason=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, loanReason);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {

                r.setLoanReason(rs.getInt("loanReason"));
                r.setLoanReasonDesc(rs.getString("loanReasonDesc"));

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
        return r;
    }

// FOR REPORTS
    public ArrayList<APCPRequest> getAllProvincialRequests(int provinceID) {
        ArrayList<APCPRequest> apcpRequestList = getAllRequests();
        ArrayList<APCPRequest> list = new ArrayList();
        ARBODAO dao = new ARBODAO();
        for (APCPRequest r : apcpRequestList) {
            ARBO arbo = dao.getARBOByID(r.getArboID());
            if (arbo.getProvOfficeCode() == provinceID) {
                list.add(r);
            }
        }
        return list;
    }

    public ArrayList<APCPRequest> getAllRegionalRequests(int regionID) {
        ArrayList<APCPRequest> apcpRequestList = getAllRequests();
        ArrayList<APCPRequest> list = new ArrayList();
        ARBODAO dao = new ARBODAO();
        for (APCPRequest r : apcpRequestList) {
            ARBO arbo = dao.getARBOByID(r.getArboID());
            if (arbo.getArboRegion() == regionID) {
                list.add(r);
            }
        }
        return list;
    }

    public ArrayList<APCPRequest> getAllARBORequests(int arboID) {
        ArrayList<APCPRequest> all = getAllRequests();
        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : all) {
            if (r.getArboID() == arboID) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public double getYearlySumOfReleasesByRequest(ArrayList<APCPRequest> apcpRequestList, int year) {
        double sum = 0;
        Calendar cal = Calendar.getInstance();

        for (APCPRequest req : apcpRequestList) {

            for (APCPRelease rel : getAllAPCPReleasesByRequest(req.getRequestID())) {
                cal.setTime(rel.getReleaseDate());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += rel.getReleaseAmount();
                }
            }

        }

        return sum;
    }

    public double getSumOfAccumulatedReleasesByRequest(ArrayList<APCPRequest> apcpRequestList) {
        double sum = 0;

        for (APCPRequest req : apcpRequestList) {
            if (req.getRequestStatus() == 5) {
                for (APCPRelease r : getAllAPCPReleasesByRequest(req.getRequestID())) {
                    sum += r.getReleaseAmount();
                }
            }
        }

        return sum;
    }

    public double getSumOfReleasesByRequest(int requestID) {
        double sum = 0;

        for (APCPRelease r : getAllAPCPReleasesByRequest(requestID)) {
            sum += r.getReleaseAmount();
        }

        return sum;
    }

    public double getYearlyTotalApprovedAmount(ArrayList<APCPRequest> apcpRequestList, int year) {
        double sum = 0;
        Calendar cal = Calendar.getInstance();

        for (APCPRequest req : apcpRequestList) {
            if (req.getRequestStatus() == 4 || req.getRequestStatus() == 5 || req.getRequestStatus() == 6) {
                cal.setTime(req.getDateApproved());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += req.getLoanAmount();
                }
            }
        }

        return sum;
    }

    public double getYearlyBudgetAllocated(ArrayList<APCPRequest> apcpRequestList, int year) {
        double sum = 0;
        Calendar cal = Calendar.getInstance();

        for (APCPRequest req : apcpRequestList) {
            if (req.getRequestStatus() == 0) { // CONDUIT
                cal.setTime(req.getDateRequested());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += req.getLoanAmount();
                }
            } else if (req.getRequestStatus() == 1) { // REQUESTED
                cal.setTime(req.getDateRequested());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += req.getLoanAmount();
                }
            } else if (req.getRequestStatus() == 2) { // CLEARED
                cal.setTime(req.getDateCleared());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += req.getLoanAmount();
                }
            } else if (req.getRequestStatus() == 3) { // ENDORSED
                cal.setTime(req.getDateEndorsed());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += req.getLoanAmount();
                }
            } else if (req.getRequestStatus() == 4) { // APPROVED
                cal.setTime(req.getDateApproved());
                if (cal.get(Calendar.YEAR) == year) {
                    sum += req.getLoanAmount();
                }
            }
        }

        return sum;
    }

    public double getTotalPastDueAmount(ArrayList<APCPRequest> requestList) {
        double sum = 0;
        for (APCPRequest req : requestList) {
            if (getAllUnsettledPastDueAccountsByRequest(req.getRequestID()).size() > 0) { // IF MAYROON
                for (PastDueAccount pda : getAllUnsettledPastDueAccountsByRequest(req.getRequestID())) {
                    sum += pda.getPastDueAmount();
                }
            }
        }

        return sum;
    }

    public double getYearlyTotalPastDueAmount(ArrayList<APCPRequest> apcpRequestList, int year) {

        double sum = 0;
        Calendar cal = Calendar.getInstance();

        for (APCPRequest req : apcpRequestList) {
            if (getAllUnsettledPastDueAccountsByRequest(req.getRequestID()).size() > 0) { // IF MAYROON
                for (PastDueAccount pda : getAllUnsettledPastDueAccountsByRequest(req.getRequestID())) {
                    cal.setTime(pda.getDateRecorded());
                    if (cal.get(Calendar.YEAR) == year) { // IF SAME YEAR
                        sum += pda.getPastDueAmount();
                    }
                }
            }
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
                    + "JOIN arbo_repayments r ON a.requestID=r.requestID "
                    + "WHERE a.arboID=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, arboID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Repayment r = new Repayment();
                r.setAmount(rs.getInt("amount"));
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

    public int getDistinctARBOCountWithReleased(ArrayList<APCPRequest> requestList, int year) {

        Calendar cal = Calendar.getInstance();
        ArrayList<Integer> filtered = new ArrayList();
        for (APCPRequest req : requestList) {
            if (req.getRequestStatus() == 5) { // RELEASED
                for (APCPRelease rel : getAllAPCPReleasesByRequest(req.getRequestID())) {
                    cal.setTime(rel.getReleaseDate());
                    if (cal.get(Calendar.YEAR) == year) {
                        if (!filtered.contains(req.getArboID())) { // CHECKS if filtered CONTAINS arboID
                            filtered.add(req.getArboID());
                        }
                    }
                }
            }
        }
        return filtered.size();
    }

    public int getDistinctARBOCountTarget(ArrayList<APCPRequest> requestList, int year) {
        Calendar cal = Calendar.getInstance();
        ArrayList<Integer> filtered = new ArrayList();
        for (APCPRequest req : requestList) {
            if (req.getRequestStatus() == 5 || req.getRequestStatus() == 4 || req.getRequestStatus() == 6) {
                cal.setTime(req.getDateApproved());
                if (cal.get(Calendar.YEAR) == year) {
                    if (!filtered.contains(req.getArboID())) { // CHECKS if filtered CONTAINS arboID
                        filtered.add(req.getArboID());
                    }
                }
            }
        }

        return filtered.size();
    }

    public int getDistinctRecipientCountWithReleased(ArrayList<APCPRequest> requestList, int year) {

        Calendar cal = Calendar.getInstance();

        ArrayList<Integer> filtered = new ArrayList();
        for (APCPRequest req : requestList) {
            if (req.getRequestStatus() == 5) {
                for (Disbursement d : getAllDisbursementsByRequest(req.getRequestID())) {
                    cal.setTime(d.getDateDisbursed());
                    if (cal.get(Calendar.YEAR) == year) {
                        if (!filtered.contains(d.getArbID())) { // CHECKS if filtered CONTAINS arbID
                            filtered.add(d.getArbID());
                        }
                    }
                }

            }
        }

        return filtered.size();
    }

    public int getDistinctRecipientCountTarget(ArrayList<APCPRequest> requestList, int year) {
        Calendar cal = Calendar.getInstance();

        ArrayList<Integer> filtered = new ArrayList();
        for (APCPRequest req : requestList) {
            if (req.getRequestStatus() == 5 || req.getRequestStatus() == 4 || req.getRequestStatus() == 6) {
                cal.setTime(req.getDateApproved());
                if (cal.get(Calendar.YEAR) == year) {
                    for (ARB arb : getAllAPCPRecipientsByRequest(req.getRequestID())) {
                        if (!filtered.contains(arb.getArbID())) { // CHECKS if filtered CONTAINS arbID
                            filtered.add(arb.getArbID());
                        }
                    }
                }
            }
        }

        return filtered.size();
    }

    public int getOnTrackRequestsPerStatus(ArrayList<APCPRequest> requestList, int status) {

        int count = 0;

        for (APCPRequest req : requestList) {
            if (req.getRequestStatus() == status) {
                int difference = 0;
                if (req.getRequestStatus() == 0) {

                    difference = getDateDiff(req.getDateRequested());

                    if (difference < 5) {
                        count++;
                    }

                } else if (req.getRequestStatus() == 1) {
                    difference = getDateDiff(req.getDateRequested());

                    if (difference < 5) {
                        count++;
                    }
                } else if (req.getRequestStatus() == 2) {

                    difference = getDateDiff(req.getDateRequested(), req.getDateCleared());

                    if (difference < 5) {
                        count++;
                    }
                } else if (req.getRequestStatus() == 3) {

                    difference = getDateDiff(req.getDateCleared(), req.getDateEndorsed());

                    if (difference < 5) {
                        count++;
                    }
                } else if (req.getRequestStatus() == 4) {

                    difference = getDateDiff(req.getDateEndorsed(), req.getDateApproved());

                    if (difference < 10) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int getDelayedRequestsPerStatus(ArrayList<APCPRequest> requestList, int status) {

        int count = 0;

        for (APCPRequest req : requestList) {
            if (req.getRequestStatus() == status) {
                int difference = 0;
                if (status == 0) {

                    difference = getDateDiff(req.getDateRequested());

                    if (difference >= 5) {
                        count++;
                    }

                } else if (status == 1) {
                    difference = getDateDiff(req.getDateRequested());

                    if (difference >= 5) {
                        count++;
                    }
                } else if (status == 2) {
                    difference = getDateDiff(req.getDateRequested(),req.getDateCleared());

                    if (difference >= 5) {
                        count++;
                    }
                } else if (status == 3) {
                    difference = getDateDiff(req.getDateCleared(),req.getDateEndorsed());

                    if (difference >= 5) {
                        count++;
                    }
                } else if (status == 4) {
                    difference = getDateDiff(req.getDateEndorsed(),req.getDateApproved());

                    if (difference >= 10) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int getDelayedRequests(ArrayList<APCPRequest> requestList) {

        int count = 0;

        for (APCPRequest req : requestList) {

            int difference = 0;
            if (req.getRequestStatus() == 0) {

                difference = getDateDiff(req.getDateRequested());

                if (difference >= 5) {
                    count++;
                }

            } else if (req.getRequestStatus() == 1) {
                difference = getDateDiff(req.getDateRequested());

                if (difference >= 5) {
                    count++;
                }
            } else if (req.getRequestStatus() == 2) {

                difference = getDateDiff(req.getDateRequested(), req.getDateCleared());

                if (difference >= 5) {
                    count++;
                }
            } else if (req.getRequestStatus() == 3) {

                difference = getDateDiff(req.getDateCleared(), req.getDateEndorsed());

                if (difference >= 5) {
                    count++;
                }
            } else if (req.getRequestStatus() == 4) {

                difference = getDateDiff(req.getDateEndorsed(), req.getDateApproved());

                if (difference >= 10) {
                    count++;
                }
            }

        }

        return count;
    }

    public int getDateDiff(Date date1) {
        long diffInMillies = System.currentTimeMillis() - date1.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public int getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public double getPercentage(int value1, int value2) {
        double value = 0;

        double valuee1 = (double) value1;
        double valuee2 = (double) value2;

        if (valuee2 > 0) {
            value = (valuee1 / valuee2) * 100;
        }
        return value;
    }

    public double getPercentage(double value1, double value2) {
        double value = 0;
        if (value2 > 0) {
            value = (value1 / value2) * 100;
        }

        return value;
    }

    public double getPercentageComparison(int value1, int value2) {
        double value = 0;

        double valuee1 = (double) value1;
        double valuee2 = (double) value2;

        if (valuee1 > 0) {
            value = ((valuee2 - valuee1) / valuee1) * 100;
        }

        return value;
    }

    public double getPercentageComparison(double value1, double value2) {
        double value = 0;

        if (value1 > 0) {
            value = ((value2 - value1) / value1) * 100;
        }

        return value;
    }

    public double getAverageDaysUnsettled(ArrayList<APCPRequest> requestList) {

        ArrayList<Integer> list = new ArrayList();

        for (APCPRequest req : requestList) {
            req.setPastDueAccounts(getAllPastDueAccountsByRequest(req.getRequestID()));
            for (PastDueAccount pda : req.getPastDueAccounts()) {
                list.add(pda.getDaysUnsettled());
            }
        }

        if (getAverage(list) > 0) {
            return getAverage(list);
        }

        return 0;

    }

    public double getAverage(ArrayList<Integer> list) {

        double total = 0;

        for (int num : list) {
            total += num;
        }

        return total / list.size();

    }

    public double getTotalApprovedAmount(ArrayList<APCPRequest> apcpRequestList) {
        double sum = 0;
        Calendar cal = Calendar.getInstance();

        for (APCPRequest req : apcpRequestList) {
            if (req.getRequestStatus() == 4 || req.getRequestStatus() == 5 || req.getRequestStatus() == 6) {
                sum += req.getLoanAmount();
            }
        }

        return sum;
    }

    public double getTotalARBOOSBalance(ArrayList<APCPRequest> apcpRequestList) {

        double sum = 0;

        for (APCPRequest req : apcpRequestList) {
            sum += req.getTotalReleaseOSBalance();
        }

        return sum;
    }

    public boolean checkHasBeenRecipient(int arbID) {

        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        Calendar current = Calendar.getInstance();
        try {
            con.setAutoCommit(false);
            String query = "SELECT ar.arbID FROM apcp_recipients ar JOIN apcp_requests a ON ar.requestID = a.requestID WHERE ar.arbID = ? AND YEAR(a.dateRequested) = ?";
            p = con.prepareStatement(query);
            p.setInt(1, arbID);
            p.setInt(2, current.get(Calendar.YEAR));

            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                success = true;
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
        return success;
    }

}
