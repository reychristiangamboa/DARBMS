/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.PastDueAccount;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class CAPDEVDAO {

    public ArrayList<CAPDEVPlan> getAllCAPDEVPlan() {

        ArrayList<CAPDEVPlan> planList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "Select * from `dar-bms`.`capdev_plans` p "
                    + "JOIN capdev_assignment r ON p.planID=r.planID "
                    + "JOIN ref_planStatus s ON r.planStatus=s.planStatus "
                    + "WHERE r.active = 1 ";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setPlanDate(rs.getDate("planDate"));
                cp.setBudget(rs.getDouble("budget"));
                cp.setPostponeReason(rs.getInt("postponeReason"));
                cp.setReason(rs.getString("reason"));
                cp.setImplementedDate(rs.getDate("implementedDate"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setClusterID(rs.getInt("clusterID"));
                cp.setObservations(rs.getString("observations"));
                cp.setRecommendation(rs.getString("recommendation"));
                cp.setActive(rs.getInt("active"));
                cp.setCapdevAssignmentID(rs.getInt("id"));
//                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));

                if (cp.getPostponeReason() > 0) {
                    cp.setPostponeReasonDesc(getPostponeReasonDesc(cp.getPostponeReason()));
                }

                planList.add(cp);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return planList;
    }

    public CAPDEVPlan getCAPDEVPlan(int planID) {
        CAPDEVPlan cp = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "Select * from `dar-bms`.`capdev_plans` p "
                    + "JOIN capdev_assignment r ON p.planID=r.planID "
                    + "JOIN ref_planStatus s ON r.planStatus=s.planStatus "
                    + "WHERE p.planID = ? AND r.active = 1";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, planID);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setBudget(rs.getDouble("budget"));
                cp.setPlanDate(rs.getDate("planDate"));
                cp.setPostponeReason(rs.getInt("postponeReason"));
                cp.setReason(rs.getString("reason"));
                cp.setImplementedDate(rs.getDate("implementedDate"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setObservations(rs.getString("observations"));
                cp.setRecommendation(rs.getString("recommendation"));
                cp.setActive(rs.getInt("active"));
                cp.setClusterID(rs.getInt("clusterID"));
                cp.setCapdevAssignmentID(rs.getInt("id"));
//                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));

                if (cp.getPostponeReason() > 0) {
                    cp.setPostponeReasonDesc(getPostponeReasonDesc(cp.getPostponeReason()));
                }
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cp;
    }

    public ArrayList<CAPDEVPlan> getAllPlanStatus() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<CAPDEVPlan> planStatus = new ArrayList();
        try {
            String query = "SELECT * FROM ref_planstatus";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CAPDEVPlan r = new CAPDEVPlan();
                r.setPlanStatus(rs.getInt("planStatus"));
                r.setPlanStatusDesc(rs.getString("planStatusDesc"));
                planStatus.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return planStatus;
    }

    public String getPostponeReasonDesc(int postponeReason) {
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        String desc = "";
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM capdev_assignment a JOIN ref_postponeReasons r ON a.postponeReason=r.postponeReason WHERE a.postponeReason=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, postponeReason);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                desc = rs.getString("postponeReasonDesc");
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return desc;
    }

    public int addCAPDEVPlan(CAPDEVPlan cp, int userID) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int n = 0;

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_plans` (`requestID`,`planDTN`,`createdBy`) VALUES (?,?,?);";
            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, cp.getRequestID());
            p.setString(2, cp.getPlanDTN());
            p.setInt(3, userID);
            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();
            if (rs.next()) {
                n = rs.getInt(1);
                p.close();

            }

            System.out.println("ITO PLAN ID oh: " + n);

            String query2 = "INSERT INTO `dar-bms`.`capdev_assignment` (`planID`,`budget`,`planDate`,`assignedTo`) VALUES (?,?,?,?);";
            PreparedStatement p2 = con.prepareStatement(query2);
            p2.setInt(1, n);
            p2.setDouble(2, cp.getBudget());
            p2.setDate(3, cp.getPlanDate());
            p2.setInt(4, cp.getAssignedTo());
            p2.executeUpdate();
            p2.close();

            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void setCAPDEVPlanDate(Date planDate, int planID) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_assignment` (`planDate`,`planID`) VALUES (?,?);";
            PreparedStatement p = con.prepareStatement(query);
            p.setDate(1, planDate);
            p.setInt(2, planID);
            p.executeUpdate();
            p.close();
            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // INCLUDES INSERT planStatus (PENDING) and active (1)

    public int addCAPDEVPlanForPastDue(CAPDEVPlan cp, int userID) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_plans` (`requestID`,`planDTN`,`createdBy`,`pastDueAccountID`,`budget`) VALUES (?,?,?,?,?);";
            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, cp.getRequestID());
            p.setString(2, cp.getPlanDTN());
            p.setInt(3, userID);
            p.setInt(4, cp.getPastDueAccountID());
            p.setDouble(5, cp.getBudget());
            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();
            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<CAPDEVPlan> getAllProvincialCAPDEVPlanByStatus(int status, int provOfficeCode) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlanByStatus(status);
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        ARBODAO dao2 = new ARBODAO();
        for (CAPDEVPlan c : allCAPDEVPlans) {

            APCPRequest r = dao.getRequestByID(c.getRequestID());
            ARBO a = dao2.getARBOByID(r.getArboID());
            if (a.getProvOfficeCode() == provOfficeCode) {
                planList.add(c);
            }

        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllProvincialCAPDEVPlanByStatus(int status, ArrayList<Integer> provinceIDs) {
        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlanByStatus(status);
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        ARBODAO dao2 = new ARBODAO();
        for (CAPDEVPlan c : allCAPDEVPlans) {
            for (int id : provinceIDs) {
                APCPRequest r = dao.getRequestByID(c.getRequestID());
                ARBO a = dao2.getARBOByID(r.getArboID());
                if (a.getProvOfficeCode() == id) {
                    planList.add(c);
                }
            }
        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllRegionalCAPDEVPlanByStatus(int status, int regCode) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlanByStatus(status);
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        ARBODAO dao2 = new ARBODAO();
        for (CAPDEVPlan c : allCAPDEVPlans) {

            APCPRequest r = dao.getRequestByID(c.getRequestID());
            ARBO a = dao2.getARBOByID(r.getArboID());
            if (a.getArboRegion() == regCode) {
                planList.add(c);
            }

        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllRegionalCAPDEVPlanByStatus(int status, ArrayList<Integer> regionIDs) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlanByStatus(status);
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        ARBODAO dao2 = new ARBODAO();
        for (CAPDEVPlan c : allCAPDEVPlans) {
            for (int id : regionIDs) {
                APCPRequest r = dao.getRequestByID(c.getRequestID());
                ARBO a = dao2.getARBOByID(r.getArboID());
                if (a.getArboRegion() == id) {
                    planList.add(c);
                }
            }
        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllProvincialCAPDEVPlan(int provOfficeCode) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlan();
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        ARBODAO dao2 = new ARBODAO();
        for (CAPDEVPlan c : allCAPDEVPlans) {

            APCPRequest r = dao.getRequestByID(c.getRequestID());
            ARBO a = dao2.getARBOByID(r.getArboID());
            if (a.getProvOfficeCode() == provOfficeCode) {
                planList.add(c);
            }

        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllRegionalCAPDEVPlan(int regCode) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlan();
        ArrayList<CAPDEVPlan> planList = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        ARBODAO dao2 = new ARBODAO();

        for (CAPDEVPlan c : allCAPDEVPlans) {
            APCPRequest r = dao.getRequestByID(c.getRequestID());
            ARBO a = dao2.getARBOByID(r.getArboID());
            if (a.getArboRegion() == regCode) {
                planList.add(c);
            }
        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllCAPDEVPlanByStatus(int status) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlan();
        ArrayList<CAPDEVPlan> planList = new ArrayList();

        for (CAPDEVPlan c : allCAPDEVPlans) {
            if (c.getPlanStatus() == status && c.getPastDueAccountID() == 0) {
                planList.add(c);
            }
        }

        return planList;
    } // PLANS W/OUT PAST DUE

    public ArrayList<CAPDEVPlan> getAllCAPDEVPlanByRequest(int requestID) {

        ArrayList<CAPDEVPlan> allCAPDEVPlans = getAllCAPDEVPlan();
        ArrayList<CAPDEVPlan> planList = new ArrayList();

        for (CAPDEVPlan c : allCAPDEVPlans) {
            if (c.getRequestID() == requestID) {
                planList.add(c);
            }
        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllRegionalCAPDEVPlanByStatusPastDue(int status, int regCode) {

        ArrayList<CAPDEVPlan> planList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM capdev_plans c "
                    + "JOIN ref_planStatus ps ON c.planStatus=ps.planStatus "
                    + "JOIN apcp_requests r ON c.requestID=r.requestID "
                    + "JOIN ref_requestStatus rs ON r.requestStatus=rs.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE c.planStatus = ? AND a.arboRegion = ? AND c.pastDueAccountID IS NOT NULL";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, regCode);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setClusterID(rs.getInt("clusterID"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                planList.add(cp);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllProvincialCAPDEVPlanByStatusPastDue(int status, int provOfficeCode) {

        ArrayList<CAPDEVPlan> planList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM capdev_plans c "
                    + "JOIN ref_planStatus ps ON c.planStatus=ps.planStatus "
                    + "JOIN apcp_requests r ON c.requestID=r.requestID "
                    + "JOIN ref_requestStatus rs ON r.requestStatus=rs.requestStatus "
                    + "JOIN arbos a ON r.arboID=a.arboID "
                    + "WHERE c.planStatus = ? AND a.provOfficeCode = ? AND c.pastDueAccountID IS NOT NULL";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, provOfficeCode);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setClusterID(rs.getInt("clusterID"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                planList.add(cp);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAssignedRequestCAPDEVPlans(int userID) {

        ArrayList<CAPDEVPlan> cpList = new ArrayList();
        ArrayList<CAPDEVPlan> allPlans = getAllCAPDEVPlan();

        for (CAPDEVPlan p : allPlans) {
            if (p.getAssignedTo() == userID && p.getPlanStatus() == 2) {
                cpList.add(p);
            }
        }

        return cpList;

    }

    public int addCAPDEVPlanActivity(CAPDEVActivity activity) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_activities` "
                    + "(`activityType`, `planID`) "
                    + "VALUES (?,?);";

            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, activity.getActivityType());
            p.setInt(2, activity.getPlanID());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();
            if (rs.next()) {
                int n = rs.getInt(1);
                p.close();
                con.commit();
                con.close();
                return n;
            }

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<CAPDEVActivity> getCAPDEVCategories() {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_activitycategory;";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public ArrayList<CAPDEVActivity> getCAPDEVPlanActivities(int planID) {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.capdev_activities a "
                    + "JOIN ref_activity r ON a.activityType=r.activityType "
                    + "JOIN ref_activityCategory c ON r.activityCategory=c.activityCategory "
                    + "WHERE a.planID=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, planID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
                ca.setActivityType(rs.getInt("activityType"));
                ca.setPlanID(rs.getInt("planID"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));

                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                ca.setTechnicalAssistant(rs.getString("technicalAssistant"));
                ca.setActive(rs.getInt("active"));
                ca.setArbList(getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public ArrayList<CAPDEVActivity> getCAPDEVActivityHistoryByRequest(int requestID) {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.capdev_activities a "
                    + "JOIN ref_activity r ON a.activityType=r.activityType "
                    + "JOIN ref_activityCategory c ON r.activityCategory=c.activityCategory "
                    + "JOIN capdev_plans p ON a.planID=p.planID "
                    + "WHERE p.requestID=? AND a.active=1;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, requestID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
                ca.setActivityType(rs.getInt("activityType"));
                ca.setPlanID(rs.getInt("planID"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                ca.setTechnicalAssistant(rs.getString("technicalAssistant"));
                ca.setActive(rs.getInt("active"));
                ca.setArbList(getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public ArrayList<CAPDEVActivity> getAPCPCAPDEVActivityHistoryByARBO(int arboID) {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.capdev_activities a "
                    + "JOIN ref_activity r ON a.activityType=r.activityType "
                    + "JOIN ref_activityCategory c ON r.activityCategory=c.activityCategory "
                    + "JOIN capdev_plans p ON a.planID=p.planID "
                    + "JOIN apcp_requests ap ON ap.requestID=p.requestID "
                    + "WHERE ap.arboID=? AND a.active=1;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, arboID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
                ca.setActivityType(rs.getInt("activityType"));
                ca.setPlanID(rs.getInt("planID"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                ca.setTechnicalAssistant(rs.getString("technicalAssistant"));
                ca.setActive(rs.getInt("active"));
                ca.setArbList(getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public ArrayList<CAPDEVActivity> getAPCPCAPDEVActivityHistoryByARB(int arbID) {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.capdev_activities a "
                    + "JOIN ref_activity r ON a.activityType=r.activityType "
                    + "JOIN ref_activityCategory c ON r.activityCategory=c.activityCategory "
                    + "JOIN capdev_participants p ON p.activityID=a.activityID "
                    + "WHERE p.arbID=? AND a.active=1 AND c.activityCategory != 3;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, arbID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
                ca.setActivityType(rs.getInt("activityType"));
                ca.setPlanID(rs.getInt("planID"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                ca.setTechnicalAssistant(rs.getString("technicalAssistant"));
                ca.setActive(rs.getInt("active"));
                ca.setIsPresent(rs.getInt("isPresent"));
                //ca.setArbList(getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public boolean addCAPDEVPlanActivityParticipants(ArrayList<ARB> arbList, int activityID) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_participants` "
                    + "(`activityID`, `arbID`) "
                    + "VALUES (?,?);";

            for (ARB arb : arbList) {
                PreparedStatement p = con.prepareStatement(query);
                p.setInt(1, activityID);
                p.setInt(2, arb.getArbID());

                p.executeUpdate();
                success = true;
                p.close();
            }

            con.commit();
            con.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<ARB> getCAPDEVPlanActivityParticipants(int activityID) {

        ArrayList<ARB> aList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ARBDAO dao = new ARBDAO();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM capdev_participants p "
                    + "JOIN capdev_activities a ON p.activityID=a.activityID "
                    + "WHERE p.activityID=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, activityID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                ARB arb = dao.getARBByID(rs.getInt("arbID"));
                arb.setIsPresent(rs.getInt("isPresent"));
                aList.add(arb);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aList;
    }

    public ArrayList<ARB> getCAPDEVPlanActivityParticipantsAttendance(int activityID, int status) {

        ArrayList<ARB> aList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ARBDAO dao = new ARBDAO();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM capdev_participants p JOIN capdev_activities a ON p.activityID=a.activityID WHERE p.activityID=? AND p.isPresent=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, activityID);
            p.setInt(2, status);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                ARB arb = dao.getARBByID(rs.getInt("arbID"));
                aList.add(arb);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aList;
    }

    public boolean addCAPDEVActivity(CAPDEVActivity cAct) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`ref_activity` (`activityName`, `activityDesc`, `activityCategory`) VALUES (?,?,?);";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, cAct.getActivityName());
            p.setString(2, cAct.getActivityDesc());
            p.setInt(3, cAct.getActivityCategory());

            p.executeUpdate();
            p.close();
            success = true;
            con.commit();
            con.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean editCAPDEVActivity(CAPDEVActivity cAct) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`ref_activity` SET `activityName`=?, `activityDesc`=?, `activityCategory`=? WHERE `activityType`=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(4, cAct.getActivityType());
            p.setString(1, cAct.getActivityName());
            p.setString(2, cAct.getActivityDesc());
            p.setInt(3, cAct.getActivityCategory());

            p.executeUpdate();
            success = true;
            p.close();

            con.commit();
            con.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean postponePlan(int planID, int postponeReason, String reason) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`capdev_assignment` SET `postponeReason`=?, `reason`=? WHERE `planID`=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, postponeReason);
            p.setString(2, reason);
            p.setInt(3, planID);

            p.executeUpdate();
            success = true;
            p.close();

            con.commit();
            con.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    } // UPDATE postponeReason

    // UPDATE budgetSpent && INSERT budgetRescheduled, planDate, pointPersonID && UPDATES PLANSTATUS (ASSIGNED)
    public boolean reschedulePlan(int planID, int pointPersonID, double budgetSpent, double budgetRescheduled, Date planDate) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int n = 0;
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`capdev_assignment` SET `budget`=? AND `active`=0 WHERE `planID`=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setDouble(1, budgetSpent);
            p.setInt(2, planID);
            p.executeUpdate();
            p.close();

            String query2 = "INSERT INTO `dar-bms`.`capdev_assignment` (`planDate`,`budget`,`assignedTo`) VALUES (?,?,?)";
            PreparedStatement p2 = con.prepareStatement(query2);
            p2.setDate(1, planDate);
            p2.setDouble(2, budgetRescheduled);
            p2.setInt(3, pointPersonID);
            p2.executeUpdate();

            ResultSet rs = p2.getGeneratedKeys();
            if (rs.next()) {
                n = rs.getInt(1);
                p2.close();
            }

            String query3 = "UPDATE `dar-bms`.`capdev_assignment` SET `planStatus`=2 WHERE `id`=?";
            PreparedStatement p3 = con.prepareStatement(query3);
            p3.setInt(1, n);
            p3.executeUpdate();
            p3.close();

            success = true;

            con.commit();
            con.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<CAPDEVPlan> getAllPostponeReasons() {
        ArrayList<CAPDEVPlan> reasons = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "Select * from `dar-bms`.`ref_postponeReasons` p";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPostponeReason(rs.getInt("postponeReason"));
                cp.setPostponeReasonDesc(rs.getString("postponeReasonDesc"));
                reasons.add(cp);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reasons;
    } // REFERENCE: REASONS

    public ArrayList<CAPDEVActivity> getCAPDEVActivities() {
        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_activity a "
                    + "JOIN `dar-bms`.ref_activityCategory c "
                    + "ON a.activityCategory=c.activityCategory ";
            PreparedStatement p = con.prepareStatement(query);

            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityType(rs.getInt("activityType"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public ArrayList<CAPDEVActivity> getCAPDEVActivitiesByCategory(int category) {
        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_activity a "
                    + "JOIN ref_activityCategory c ON a.activityCategory=c.activityCategory "
                    + "WHERE c.activityCategory = ?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, category);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityType(rs.getInt("activityType"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                caList.add(ca);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caList;
    }

    public boolean checkPastDueAccount(int arboID) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT COUNT(*) FROM past_due_accounts p "
                    + "JOIN apcp_requests r ON p.requestID=r.requestID "
                    + "WHERE r.arboID=? AND p.active=1";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, arboID);

            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                if (rs.getInt("COUNT(*)") == 1) {
                    success = true;
                }
            }
            p.close();
            rs.close();
            con.commit();
            con.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean assignPointPerson(int planID, int userID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_assignment SET `assignedTo`=? WHERE `planID`=?";
            p = con.prepareStatement(query);
            p.setInt(1, userID);
            p.setInt(2, planID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean updatePlanStatus(int planID, int status) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_assignment SET `planStatus`=? WHERE `planID`=?";
            p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, planID);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean updatePlanStatus(int planID, int status, Date implementedDate) {
        boolean success = false;
        PreparedStatement p = null;
        PreparedStatement p2 = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_assignment SET `planStatus`=? WHERE `planID`=?";
            p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, planID);

            String query2 = "UPDATE capdev_plans SET `implementedDate`=? WHERE `planID`=?";
            p2 = con.prepareStatement(query2);
            p2.setDate(1, implementedDate);
            p2.setInt(2, planID);

            p.executeUpdate();
            p2.executeUpdate();
            p.close();
            p2.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean addPastDueReason(String reason) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO ref_reasonPastDue (reasonPastDueDesc) VALUES (?)";
            p = con.prepareStatement(query);
            p.setString(1, reason);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean editPastDueReason(String reason, int reasonPastDue) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE ref_reasonPastDue SET reasonPastDueDesc=? WHERE reasonPastDue=?";
            p = con.prepareStatement(query);
            p.setString(1, reason);
            p.setInt(2, reasonPastDue);

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<PastDueAccount> getAllPastDueReasons() {
        ArrayList<PastDueAccount> paList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_reasonPastDue;";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                PastDueAccount pa = new PastDueAccount();
                pa.setReasonPastDue(rs.getInt("reasonPastDue"));
                pa.setReasonPastDueDesc(rs.getString("reasonPastDueDesc"));
                paList.add(pa);
            }
            con.commit();
            con.close();
            rs.close();
            p.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paList;
    }

    public int getPastDueReasonByDesc(String reason) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_reasonPastDue WHERE reasonPastDueDesc=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, reason);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                id = rs.getInt("reasonPastDue");
            }
            con.commit();
            con.close();
            rs.close();
            p.close();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean recordAssessment(CAPDEVActivity ca) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_activities SET active=? WHERE activityID=?";

            p = con.prepareStatement(query);
            p.setInt(1, ca.getActive());
            p.setInt(2, ca.getActivityID());

            p.executeUpdate();
            p.close();

            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean checkIfPresent(ArrayList<Integer> arbIDs, int activityID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_participants SET isPresent=1 WHERE arbID=? AND activityID=?";

            for (int i : arbIDs) {
                p = con.prepareStatement(query);
                p.setInt(1, i);
                p.setInt(2, activityID);
                p.executeUpdate();
                p.close();
            }

            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public void replaceAttendees(ArrayList<Integer> attendees, int activityID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_participants SET isPresent=2 WHERE arbID=? AND activityID=?";

            for (int i : attendees) {
                p = con.prepareStatement(query);
                p.setInt(1, i);
                p.setInt(2, activityID);
                p.executeUpdate();
                p.close();
            }

            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTechnicalAssistant(String name, int activityID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_activities SET technicalAssistant=?, active = 1 WHERE activityID=?";

            p = con.prepareStatement(query);
            p.setString(1, name);
            p.setInt(2, activityID);
            p.executeUpdate();
            p.close();

            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void assessCAPDEV(String observation, String recommendation, int planID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE capdev_plans SET `observations`=?, `recommendation`=? WHERE `planID`=?";

            p = con.prepareStatement(query);
            p.setString(1, observation);
            p.setString(2, recommendation);
            p.setInt(3, planID);

            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CAPDEVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getDistinctARBOCountWithImplemented(ArrayList<CAPDEVPlan> planList, int year) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ArrayList<Integer> filtered = new ArrayList();
        Calendar cal = Calendar.getInstance();
        for (CAPDEVPlan c : planList) {

            cal.setTime(c.getImplementedDate());
            if (cal.get(Calendar.YEAR) == year) {
                APCPRequest request = requestDAO.getRequestByID(c.getRequestID());
                if (!filtered.contains(request.getArboID())) {
                    filtered.add(request.getArboID());
                }
            }

        }

        return filtered.size();
    }

    public int getDistinctARBOCountTarget(ArrayList<CAPDEVPlan> planList, int year) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ArrayList<Integer> filtered = new ArrayList();
        Calendar cal = Calendar.getInstance();
        for (CAPDEVPlan c : planList) {
            if (c.getPlanStatus() == 5 || c.getPlanStatus() == 4) {
                cal.setTime(c.getPlanDate());
                if (cal.get(Calendar.YEAR) == year) {
                    APCPRequest request = requestDAO.getRequestByID(c.getRequestID());
                    if (!filtered.contains(request.getArboID())) {
                        filtered.add(request.getArboID());
                    }
                }
            }
        }

        return filtered.size();
    }

    public int getDistinctParticipantCountWithImplemented(ArrayList<CAPDEVPlan> planList, int year) {

        
        ArrayList<Integer> filtered = new ArrayList();
        Calendar cal = Calendar.getInstance();

        for (CAPDEVPlan c : planList) {
            cal.setTime(c.getImplementedDate());
            if (cal.get(Calendar.YEAR) == year) {
                c.setActivities(getCAPDEVPlanActivities(c.getPlanID()));
                for (CAPDEVActivity act : c.getActivities()) {
                    for (ARB arb : act.getArbList()) {
                        if (!filtered.contains(arb.getArbID())) {
                            filtered.add(arb.getArbID());
                        }
                    }
                }
            }
        }

        return filtered.size();
    }

    public int getDistinctParticipantCountTarget(ArrayList<CAPDEVPlan> planList, int year) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ARBODAO arboDAO = new ARBODAO();
        ARBDAO arbDAO = new ARBDAO();
        ArrayList<Integer> filtered = new ArrayList();
        Calendar cal = Calendar.getInstance();

        for (CAPDEVPlan c : planList) {

            if (c.getPlanStatus() == 5 || c.getPlanStatus() == 4) {
                cal.setTime(c.getPlanDate());
                if (cal.get(Calendar.YEAR) == year) {
                    APCPRequest request = requestDAO.getRequestByID(c.getRequestID());
                    ARBO arbo = arboDAO.getARBOByID(request.getArboID());
                    arbo.setArbList(arbDAO.getAllARBsARBO(arbo.getArboID()));
                    for (ARB arb : arbo.getArbList()) {
                        if (!filtered.contains(arb.getArbID())) {
                            filtered.add(arb.getArbID());
                        }
                    }
                }
            }
        }

        return filtered.size();
    }

    public double getCumulativeApprovedAmount(ArrayList<CAPDEVPlan> planList) {
        double sum = 0;
        for (CAPDEVPlan plan : planList) {
            if (plan.getPlanStatus() == 4) { // ASSIGNED
                sum += plan.getBudget();
            }
        }

        return sum;
    }

    public double getCumulativeBudgetSpent(ArrayList<CAPDEVPlan> planList) {
        double sum = 0;
        for (CAPDEVPlan plan : planList) {
            if (plan.getPlanStatus() == 5) { // IMPLEMENTED
                sum += plan.getBudget();
            }
        }

        return sum;
    }

    public double getYearlyImplementedBudget(ArrayList<CAPDEVPlan> planList, int year) {
        double sum = 0;
        Calendar cal = Calendar.getInstance();
        for (CAPDEVPlan plan : planList) {
            cal.setTime(plan.getImplementedDate());
            if (cal.get(Calendar.YEAR) == year) {
                sum += plan.getBudget();
            }
        }

        return sum;
    }

    public int getYearlyImplementedCount(ArrayList<CAPDEVPlan> planList, int year) {
        int count = 0;
        Calendar cal = Calendar.getInstance();
        for (CAPDEVPlan plan : planList) {
            cal.setTime(plan.getImplementedDate());
            if (cal.get(Calendar.YEAR) == year) {
                count++;
            }
        }

        return count;
    }

    public int getYearlyPlanCount(ArrayList<CAPDEVPlan> planList, int year) {
        int count = 0;
        Calendar cal = Calendar.getInstance();
        for (CAPDEVPlan plan : planList) {
            if (plan.getPlanStatus() == 5 || plan.getPlanStatus() == 4) {
                cal.setTime(plan.getPlanDate());
                if (cal.get(Calendar.YEAR) == year) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getOnTrackPlansPerStatus(ArrayList<CAPDEVPlan> planList, int status) {

        int count = 0;

        for (CAPDEVPlan plan : planList) {
            if (plan.getPlanStatus() == status) {
                int difference = 0;
                if (status == 1 || status == 2 || status == 4) {

                    difference = getDateDiff(plan.getPlanDate());

                    if (difference < 5) {
                        count++;
                    }

                }
            }
        }

        return count;
    }

    public int getDelayedPlansPerStatus(ArrayList<CAPDEVPlan> planList, int status) {

        int count = 0;

        for (CAPDEVPlan plan : planList) {
            if (plan.getPlanStatus() == status) {
                int difference = 0;
                if (status == 1 || status == 2 || status == 4) {

                    difference = getDateDiff(plan.getPlanDate());

                    if (difference >= 5) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public int getDateDiff(Date date1) {
        long diffInMillies = System.currentTimeMillis() - date1.getTime();
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public double getPercentage(int value1, int value2) {
        double value = 0;
        if (value2 > 0) {
            value = (value1 / value2) * 100;
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

}
