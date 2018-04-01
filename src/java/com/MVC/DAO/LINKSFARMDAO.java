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
import com.MVC.Model.Cluster;
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
public class LINKSFARMDAO {

    public int addCluster(String name, int cityMunCode) {
        PreparedStatement pstmt = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`clusters` (`clusterName`,`clusterSite`) VALUES (?,?);";
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setInt(2, cityMunCode);

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                int n = rs.getInt(1);
                pstmt.close();
                rs.close();
                con.commit();
                con.close();
                return n;
            }

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public ArrayList<Cluster> getClustersBySite(int projectSite) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Cluster> clusterList = new ArrayList();
        
        try {
            String query = "SELECT * FROM `dar-bms`.clusters c "
                    + "JOIN refcitymun r ON c.clusterSite=r.citymunCode "
                    + "WHERE c.clusterSite=? ";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, projectSite);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cluster c = new Cluster();
                c.setClusterID(rs.getInt("clusterID"));
                c.setClusterName(rs.getString("clusterName"));
                c.setClusterSite(projectSite);
                c.setClusterSiteDesc(rs.getString("cityMunDesc"));
                c.setClusterMembers(getClusterMembersByID(rs.getInt("clusterID")));
                clusterList.add(c);
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clusterList;
    }
    
    public Cluster getClusterByID(int clusterID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        Cluster c = new Cluster();
        try {
            String query = "SELECT * FROM `dar-bms`.clusters c JOIN refcitymun r ON c.clusterSite=r.citymunCode WHERE `clusterID`=? ";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, clusterID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                c.setClusterID(rs.getInt("clusterID"));
                c.setClusterName(rs.getString("clusterName"));
                c.setClusterSite(clusterID);
                c.setClusterSiteDesc(rs.getString("cityMunDesc"));
                c.setClusterMembers(getClusterMembersByID(rs.getInt("clusterID")));
            } else {
                return null;
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public ArrayList<ARB> getClusterMembersByID(int clusterID){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARB> arbList = new ArrayList();
        ARBDAO dao = new ARBDAO();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.ref_educationLevel e ON a.educationLevel=e.educationLevel "
                    + "JOIN `dar-bms`.ref_arbStatus s ON a.arbStatus=s.arbStatus "
                    + "JOIN `dar-bms`.refbrgy b ON a.brgyCode=b.brgyCode "
                    + "JOIN `dar-bms`.refcitymun c ON a.cityMunCode=c.citymunCode "
                    + "JOIN `dar-bms`.refprovince p ON a.provCode=p.provCode "
                    + "JOIN `dar-bms`.refregion r ON a.regCode=r.regCode "
                    + "WHERE a.clusterID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, clusterID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARB arb = new ARB();
                arb.setArbID(rs.getInt("arbID"));
                arb.setArboID(rs.getInt("arboID"));
                arb.setFirstName(rs.getString("firstName"));
                arb.setMiddleName(rs.getString("middleName"));
                arb.setLastName(rs.getString("lastName"));
                arb.setMemberSince(rs.getDate("memberSince"));
                arb.setArbUnitNumStreet(rs.getString("arbUnitNumStreet"));
                arb.setBrgyCode(rs.getInt("brgyCode"));
                arb.setBrgyDesc(rs.getString("brgyDesc"));
                arb.setCityMunCode(rs.getInt("cityMunCode"));
                arb.setCityMunDesc(rs.getString("citymunDesc"));
                arb.setProvCode(rs.getInt("provCode"));
                arb.setProvDesc(rs.getString("provDesc"));
                arb.setRegCode(rs.getInt("regCode"));
                arb.setRegDesc(rs.getString("regDesc"));
                arb.setGender(rs.getString("gender"));
                arb.setEducationLevel(rs.getInt("educationLevel"));
                arb.setEducationLevelDesc(rs.getString("educationLevelDesc"));
                arb.setLandArea(rs.getDouble("landArea"));
                arb.setCurrentCrops(dao.getAllARBCurrentCrops(rs.getInt("arbID")));
                arb.setCrops(dao.getAllARBCrops(rs.getInt("arbID")));
                arb.setDependents(dao.getAllARBDependents(rs.getInt("arbID")));
                arb.setArbStatus(rs.getInt("arbStatus"));
                arb.setArbStatusDesc(rs.getString("arbStatusDesc"));
                arb.setClusterID(rs.getInt("clusterID"));
                arbList.add(arb);
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arbList;
    }
    
    

    public boolean setClusterID(ArrayList<ARB> arbList, int clusterID) {
        PreparedStatement pstmt = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        boolean success = false;
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`arbs` SET clusterID=? WHERE arbID=?;";

            for (ARB arb : arbList) {
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, clusterID);
                pstmt.setInt(2, arb.getArbID());

                pstmt.executeUpdate();
                pstmt.close();
                success = true;
            }

            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean setToActiveProjectSite(int cityMunCode) {
        PreparedStatement pstmt = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        boolean success = false;
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`refcitymun` SET isProjectSite=1 WHERE cityMunCode=?;";
            pstmt = con.prepareStatement(query);
            
            pstmt.setInt(1, cityMunCode);
            pstmt.executeUpdate();
            
            pstmt.close();
            con.commit();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public ArrayList<CAPDEVActivity> getAPCPCAPDEVActivityHistoryByCluster(int clusterID) {

        ArrayList<CAPDEVActivity> caList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        CAPDEVDAO dao = new CAPDEVDAO();
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.capdev_activities a "
                    + "JOIN ref_activity r ON a.activityType=r.activityType "
                    + "JOIN ref_activityCategory c ON r.activityCategory=c.activityCategory "
                    + "JOIN capdev_plans p ON a.planID=p.planID "
                    + "WHERE p.clusterID=? AND a.active=1;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, clusterID);
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
                ca.setActivityCategory(rs.getInt("activityCategory"));
                ca.setActivityCategoryDesc(rs.getString("activityCategoryDesc"));
                ca.setTechnicalAssistant(rs.getString("technicalAssistant"));
                ca.setActive(rs.getInt("active"));
                ca.setArbList(dao.getCAPDEVPlanActivityParticipants(rs.getInt("activityID")));
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
    
    public int addCAPDEVPlan(CAPDEVPlan cp, int userID) {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`capdev_plans` "
                    + "(`clusterID`,`planDTN`,`createdBy`) "
                    + "VALUES (?,?,?);";
            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, cp.getClusterID());
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
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public ArrayList<CAPDEVPlan> getAllLINKSFARMCAPDEVPlanByStatus(int status) {

        ArrayList<CAPDEVPlan> planList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        CAPDEVDAO dao = new CAPDEVDAO();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM capdev_plans c "
                    + "JOIN ref_planStatus ps ON c.planStatus=ps.planStatus "
                    + "JOIN clusters cl ON c.clusterID=cl.clusterID "
                    + "WHERE c.planStatus = ? AND c.clusterID IS NOT NULL";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, status);
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
                cp.setActivities(dao.getCAPDEVPlanActivities(rs.getInt("planID")));
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
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return planList;
    }
    
    public ArrayList<CAPDEVPlan> getAssignedRequestCAPDEVPlans(int userID) {

        ArrayList<CAPDEVPlan> cpList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        CAPDEVDAO dao = new CAPDEVDAO();
        try {
            con.setAutoCommit(false);
            String query = "select * from capdev_plans p "
                    + "join ref_planStatus s on p.planStatus=s.planStatus "
                    + "where p.assignedTo=? AND p.planStatus=4 AND p.clusterID IS NOT NULL";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                CAPDEVPlan cp = new CAPDEVPlan();
                cp = new CAPDEVPlan();
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
                cp.setActivities(dao.getCAPDEVPlanActivities(rs.getInt("planID")));
                cpList.add(cp);
            }
            con.commit();
            rs.close();
            p.close();
            con.close();

        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(LINKSFARMDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cpList;
    }

}
