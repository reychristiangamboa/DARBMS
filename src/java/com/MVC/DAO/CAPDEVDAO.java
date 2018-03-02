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

    public int addCAPDEVPlan(CAPDEVPlan cp) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_plans` (`requestID`,`planDTN`) VALUES (?,?);";
            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, cp.getRequestID());
            p.setString(2, cp.getPlanDTN());
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
                    + "(`requestID`, `assignedTo`) "
                    + "VALUES (?,?);";

            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, plan.getRequestID());
            p.setInt(2, plan.getAssignedTo());
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
            String query = "Select * from `dar-bms`.`capdev_plans` WHERE planID = ?";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, planID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
                cp.setPastDueAccountID(rs.getInt("pastDueAccountID"));
            }
            con.close();
            con.commit();
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

    public int addCAPDEVPlanActivity(CAPDEVActivity activity) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_activities` "
                    + "(`activityID`, `planID`, `activityDate`) "
                    + "VALUES (?,?,?);";

            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, activity.getActivityID());
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
            String query = "SELECT * FROM `dar-bms`.capdev_activities a JOIN ref_activity r ON a.activityID=r.activityID WHERE planID=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, planID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
                ca.setPlanID(rs.getInt("planID"));
                ca.setActivityName(rs.getString("activityName"));
                ca.setActivityDate(rs.getDate("activityDate"));
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

    public boolean addCAPDEVPlanActivityParticipants(ArrayList<ARB> arbList, int capdevActivityID) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_participants` "
                    + "(`capdevActivityID`, `arbID`) "
                    + "VALUES (?,?);";

            for (ARB arb : arbList) {
                PreparedStatement p = con.prepareStatement(query);
                p.setInt(1, capdevActivityID);
                p.setInt(2, arb.getArbID());

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
        return success;
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
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
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
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
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
            String query = "select * from capdev_plans p where p.assignedTo =? AND p.planStatus=2";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp.setPlanID(rs.getInt("planID"));
                cp.setRequestID(rs.getInt("requestID"));
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
            String query = "INSERT INTO `dar-bms`.`ref_activity` (`activityName`, `activityDesc`)"
                    + "VALUES (?,?);";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, cAct.getActivityName());
            p.setString(2, cAct.getActivityDesc());

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
        return success;
    }

    public ArrayList<CAPDEVActivity> getCAPDEVActivities() {
        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.ref_activity WHERE activityID != 1;";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVActivity ca = new CAPDEVActivity();
                ca.setActivityID(rs.getInt("activityID"));
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
    
    public boolean updatePlanStatus(int planID, int status){
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
    
    public boolean addPastDueReason(String reason){
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
    
    public boolean editPastDueReason(String reason, int reasonPastDue){
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
    
    
}
