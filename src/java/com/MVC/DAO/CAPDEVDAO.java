/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.ARB;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.PastDueAccount;
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
public class CAPDEVDAO {

    public int addCAPDEVPlan(CAPDEVPlan cp, int userID) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

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

    public int addPreReleaseOrientation(CAPDEVPlan plan) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_plans` "
                    + "(`requestID`, `assignedTo`,`createdBy`,`planDTN`, `planStatus`) "
                    + "VALUES (?,?,?,?,?);";

            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, plan.getRequestID());
            p.setInt(2, plan.getAssignedTo());
            p.setInt(3, plan.getCreatedBy());
            p.setString(4, plan.getPlanDTN());
            p.setInt(5, plan.getPlanStatus());
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

    public CAPDEVPlan getCAPDEVPlan(int planID) {
        CAPDEVPlan cp = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "Select * from `dar-bms`.`capdev_plans` p JOIN ref_planStatus s ON p.planStatus=s.planStatus WHERE planID = ?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, planID);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
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

        return cp;
    }

    public ArrayList<CAPDEVPlan> getAllProvincialCAPDEVPlanByStatus(int status, int provOfficeCode) {

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
                    + "WHERE c.planStatus = ? AND a.provOfficeCode = ?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, provOfficeCode);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                planList.add(cp);
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

        return planList;
    }

    public ArrayList<CAPDEVPlan> getAllRegionalCAPDEVPlanByStatus(int status, int regCode) {

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
                    + "WHERE c.planStatus = ? AND a.arboRegion = ?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, regCode);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                planList.add(cp);
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

        return planList;
    }

    public int addCAPDEVPlanActivity(CAPDEVActivity activity) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_activities` "
                    + "(`activityType`, `planID`, `activityDate`) "
                    + "VALUES (?,?,?);";

            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, activity.getActivityType());
            p.setInt(2, activity.getPlanID());
            p.setDate(3, activity.getActivityDate());

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

    public ArrayList<CAPDEVActivity> getCAPDEVPlanActivities(int planID) {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.capdev_activities a JOIN ref_activity r ON a.activityType=r.activityType WHERE planID=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, planID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
                ca.setActivityType(rs.getInt("activityType"));
                ca.setPlanID(rs.getInt("planID"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDate(rs.getDate("activityDate"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                ca.setObservations(rs.getString("observations"));
                ca.setRecommendation(rs.getString("recommendation"));
                ca.setArbList(getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
                caList.add(ca);
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
                ca.setActivityDate(rs.getDate("activityDate"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                ca.setObservations(rs.getString("observations"));
                ca.setRecommendation(rs.getString("recommendation"));
                ca.setArbList(getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
                caList.add(ca);
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
            String query = "SELECT * FROM capdev_participants p JOIN capdev_activities a ON p.activityID=a.activityID WHERE p.activityID=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, activityID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                ARB arb = dao.getARBByID(rs.getInt("arbID"));
                aList.add(arb);
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

    public ArrayList<CAPDEVPlan> getPendingRequestCAPDEVPlans(int requestID) {

        ArrayList<CAPDEVPlan> cpList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * from capdev_plans p join apcp_requests r where r.requestID =? AND p.planStatus=1";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, requestID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                cpList.add(cp);
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
        return cpList;
    }

    public ArrayList<CAPDEVPlan> getApprovedRequestCAPDEVPlans(int requestID) {

        ArrayList<CAPDEVPlan> cpList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "select * from capdev_plans p join apcp_requests r where r.requestID =? AND p.planStatus=2";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, requestID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                cpList.add(cp);
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
        return cpList;
    }

    public ArrayList<CAPDEVPlan> getAssignedRequestCAPDEVPlans(int userID) {

        ArrayList<CAPDEVPlan> cpList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "select * from capdev_plans p join ref_planStatus s on p.planStatus=s.planStatus where p.assignedTo=? AND p.planStatus=4";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                cpList.add(cp);
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
        return cpList;
    }
    
    public ArrayList<CAPDEVPlan> getAssignedPreReleasePlans(int userID) {

        ArrayList<CAPDEVPlan> cpList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "select * from capdev_plans p join ref_planStatus s on p.planStatus=s.planStatus where p.assignedTo=? AND p.planStatus=6";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
                cp.setAssignedTo(rs.getInt("assignedTo"));
                cp.setPlanStatus(rs.getInt("planStatus"));
                cp.setPlanStatusDesc(rs.getString("planStatusDesc"));
                cp.setPlanDTN(rs.getString("planDTN"));
                cp.setCreatedBy(rs.getInt("createdBy"));
                cp.setApprovedBy(rs.getInt("approvedBy"));
                cp.setActivities(getCAPDEVPlanActivities(rs.getInt("planID")));
                cpList.add(cp);
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
        return cpList;
    }

    public boolean addCAPDEVActivity(CAPDEVActivity cAct) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`ref_activity` (`activityName`, `activityDesc`) VALUES (?,?);";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, cAct.getActivityName());
            p.setString(2, cAct.getActivityDesc());

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
            String query = "UPDATE `dar-bms`.`ref_activity` SET `activityName`=?, `activityDesc`=? WHERE `activityType`=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(3, cAct.getActivityType());
            p.setString(1, cAct.getActivityName());
            p.setString(2, cAct.getActivityDesc());

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

    public ArrayList<CAPDEVActivity> getCAPDEVActivities() {
        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_activity WHERE activityType != 1;";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityType(rs.getInt("activityType"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDesc(rs.getString("activityDesc"));
                caList.add(ca);
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
            String query = "UPDATE capdev_plans SET `assignedTo`=? WHERE `planID`=?";
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
            String query = "UPDATE capdev_plans SET `planStatus`=? WHERE `planID`=?";
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
    
    public int getPastDueReasonByDesc(String reason){
        ArrayList<PastDueAccount> paList = new ArrayList();
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
            String query = "UPDATE capdev_activities SET observations=?, recommendation=?, active=? WHERE activityID=?";

            p = con.prepareStatement(query);
            p.setString(1, ca.getObservations());
            p.setString(2, ca.getRecommendation());
            p.setInt(3, ca.getActive());
            p.setInt(4, ca.getActivityID());
            
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

    
    
    
}
