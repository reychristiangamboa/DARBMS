/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.Crop;
import com.MVC.Model.Dependent;
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
public class ARBDAO {

    public ARB getARBByID(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ARB arb = new ARB();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.ref_educationLevel e ON a.educationLevel=e.educationLevel "
                    + "JOIN `dar-bms`.ref_arbStatus s ON a.arbStatus=s.arbStatus "
                    + "JOIN `dar-bms`.refbrgy b ON a.brgyCode=b.brgyCode "
                    + "JOIN `dar-bms`.refcitymun c ON a.cityMunCode=c.citymunCode "
                    + "JOIN `dar-bms`.refprovince p ON a.provCode=p.provCode "
                    + "JOIN `dar-bms`.refregion r ON a.regCode=r.regCode "
                    + "WHERE `arbID`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
                arb.setCrops(getAllARBCrops(rs.getInt("arbID")));
                arb.setDependents(getAllARBDependents(rs.getInt("arbID")));
                arb.setArbRating(rs.getDouble("arbRating"));
                arb.setArbStatus(rs.getInt("arbStatus"));
                arb.setArbStatusDesc(rs.getString("arbStatusDesc"));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arb;
    }

    public ArrayList<ARB> getAllARBsARBO(int arboID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARB> arbList = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.ref_educationLevel e ON a.educationLevel=e.educationLevel "
                    + "JOIN `dar-bms`.ref_arbStatus s ON a.arbStatus=s.arbStatus "
                    + "JOIN `dar-bms`.refbrgy b ON a.brgyCode=b.brgyCode "
                    + "JOIN `dar-bms`.refcitymun c ON a.cityMunCode=c.citymunCode "
                    + "JOIN `dar-bms`.refprovince p ON a.provCode=p.provCode "
                    + "JOIN `dar-bms`.refregion r ON a.regCode=r.regCode "
                    + "WHERE `arboID`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arboID);
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
                arb.setCrops(getAllARBCrops(rs.getInt("arbID")));
                arb.setDependents(getAllARBDependents(rs.getInt("arbID")));
                arb.setArbRating(rs.getDouble("arbRating"));
                arb.setArbStatus(rs.getInt("arbStatus"));
                arb.setArbStatusDesc(rs.getString("arbStatusDesc"));
                arbList.add(arb);
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arbList;
    }
    
    public ArrayList<Crop> getAllARBCrops(int arbID){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Crop> cropList = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.crops c ON a.arbID=c.arbID "
                    + "JOIN `dar-bms`.ref_cropType ct ON c.cropTag=ct.cropType "
                    + "WHERE a.arbID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Crop c = new Crop();
                c.setCropType(rs.getInt("cropTag"));
                c.setCropTypeDesc(rs.getString("cropTypeDesc"));
                cropList.add(c);
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cropList;
    }
    
    public ArrayList<Dependent> getAllARBDependents(int arbID){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Dependent> dependentList = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.dependents d ON a.arbID=d.arbID "
                    + "JOIN `dar-bms`.ref_educationLevel e ON d.educationLevel=e.educationLevel "
                    + "WHERE a.arbID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Dependent d = new Dependent();
                d.setName(rs.getString("name"));
                d.setBirthday(rs.getDate("birthday"));
                d.setEducationLevel(rs.getInt("educationLevel"));
                d.setEducationLevelDesc(rs.getString("educationLevelDesc"));
                dependentList.add(d);
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dependentList;
    }

    public int addARB(ARB arb) {
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`arbs` (`arboID`, `arboRepresentative`, `firstName`, `middleName`, "
                    + "`lastName`, `memberSince`,`arbUnitNumStreet`,`brgyCode`,`cityMunCode`,`provCode`,`regCode`, "
                    + "`gender`, `educationLevel`, `landArea`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setInt(1, arb.getArboID());
            p.setInt(2, arb.getArboRepresentative());
            p.setString(3, arb.getFirstName());
            p.setString(4, arb.getMiddleName());
            p.setString(5, arb.getLastName());
            p.setDate(6, arb.getMemberSince());
            p.setString(7, arb.getArbUnitNumStreet());
            p.setInt(8, arb.getBrgyCode());
            p.setInt(9, arb.getCityMunCode());
            p.setInt(10, arb.getProvCode());
            p.setInt(11, arb.getRegCode());
            p.setString(12, arb.getGender());
            p.setInt(13, arb.getEducationLevel());
            p.setDouble(14, arb.getLandArea());

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
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean addCrops(int arbID, ArrayList<Crop> cropList) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`crops` (`arbID`, `cropTag`,`startDate`,`endDate`) VALUES (?, ?, ?, ?);";

            for (Crop crop : cropList) {
                p = con.prepareStatement(query);
                p.setInt(1, arbID);
                p.setInt(2, crop.getCropType());
                p.setDate(3, crop.getStartDate());
                p.setDate(4, crop.getEndDate());
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
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public boolean addDependents(int arbID, ArrayList<Dependent> dependentList) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`dependents` (`arbID`, `name`, `birthday`, `educationLevel`) VALUES (?, ?, ?, ?);";

            for (Dependent d : dependentList) {
                p = con.prepareStatement(query);
                p.setInt(1, arbID);
                p.setString(2, d.getName());
                p.setDate(3, d.getBirthday());
                p.setInt(4, d.getEducationLevel());
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
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public int getEducationalLevel(String educationalLevelDesc) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.ref_educationLevel WHERE `educationLevelDesc`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, educationalLevelDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("educationLevel");
            }
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public int getCrop(String cropTypeDesc) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.ref_cropType WHERE `cropTypeDesc`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, cropTypeDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("cropType");
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public int getARBID(String fN, String mN, String lN) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.arbs WHERE `firstName`=? AND `middleName`=? AND `lastName`=?;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, fN);
            pstmt.setString(2, mN);
            pstmt.setString(3, lN);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("arbID");
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}