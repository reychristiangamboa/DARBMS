/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
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
public class ARBODAO {

    public ARBO getARBOByID(int id) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ARBO arbo = new ARBO();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "WHERE a.arboID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
            } else {
                return null;
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arbo;
    }

    public boolean addARBO(ARBO arbo) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`arbos` (`arboName`, `arboCityMun`, `arboProvince`, "
                    + "`arboRegion`,`provOfficeCode`, `arboType`, `APCPQualified`, `arboID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setString(1, arbo.getArboName());
            p.setInt(2, arbo.getArboCityMun());
            p.setInt(3, arbo.getArboProvince());
            p.setInt(4, arbo.getArboRegion());
            p.setInt(5, arbo.getProvOfficeCode());
            p.setInt(6, arbo.getArboType());
            p.setInt(7, arbo.getAPCPQualified());
            p.setInt(8, arbo.getArboID());

            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<ARBO> getAllARBOs() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
                arboList.add(arbo);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboList;
    }

    public ArrayList<ARBO> getAllARBOsByRegion(int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "WHERE a.arboRegion=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
                arboList.add(arbo);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboList;
    }

    public ArrayList<ARBO> getAllARBOsByProvince(int provinceOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "WHERE a.provOfficeCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceOfficeCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
                arboList.add(arbo);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboList;
    }

    public ArrayList<ARBO> getAllARBOsByCityMun(int cityMun) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "WHERE a.arboCityMun=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, cityMun);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
                arboList.add(arbo);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboList;
    }

    public ArrayList<ARBO> getAllNonQualifiedARBOs(int provinceID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "WHERE a.provOfficeCode=? AND APCPQualified = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
                arboList.add(arbo);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboList;
    }

    public ArrayList<ARBO> getAllQualifiedARBOs(int provinceID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "WHERE a.provOfficeCode=? AND APCPQualified = 1";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
                arbo.setArbList(getAllARBsARBO(rs.getInt("arboID")));
                arboList.add(arbo);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboList;
    }

    public boolean updateARBOStatus(int arboID, int status) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE arbos SET `APCPQualified`=? WHERE `arboID`=?";
            p = con.prepareStatement(query);
            p.setInt(1, status);
            p.setInt(2, arboID);

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

    public boolean checkIfARBOExist(String arboName) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();

        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.arbos;";
            p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase(arboName)) {
                    success = true;
                }
            }

            rs.close();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<ARB> getAllARBsARBO(int arboID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ARBDAO dao = new ARBDAO();
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
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arbList;
    }

    public int getARBOType(String arboTypeDesc) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int arboType = 0;
        try {
            String query = "SELECT * FROM ref_arboType WHERE arboTypeDesc = ?";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                arboType = rs.getInt("arboType");
            }
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboType;
    }

}
