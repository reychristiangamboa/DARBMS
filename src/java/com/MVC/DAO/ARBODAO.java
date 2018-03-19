/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
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
                    + "JOIN users u ON u.provOfficeCode=po.provOfficeCode "
                    + "WHERE a.arboID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provOfficeDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
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
            String query = "INSERT INTO `dar-bms`.`arbos` (`arboName`, `arboCityMun`, `arboProvince`, `arboRegion`,`provOfficeCode`) "
                    + "VALUES (?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setString(1, arbo.getArboName());
            p.setInt(2, arbo.getArboCityMun());
            p.setInt(3, arbo.getArboProvince());
            p.setInt(4, arbo.getArboRegion());
            p.setInt(5, arbo.getProvOfficeCode());

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

    public boolean addARBOExcel(ArrayList<ARBO> arboList) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`arbos` (`arboName`, `arboCityMun`, `arboProvince`, `arboRegion`, `provOfficeCode`) "
                    + "VALUES (?, ?, ?, ?, ?);";
            for (ARBO arbo : arboList) {
                p = con.prepareStatement(query);
                p.setString(1, arbo.getArboName());
                p.setInt(2, arbo.getArboCityMun());
                p.setInt(3, arbo.getArboProvince());
                p.setInt(4, arbo.getArboRegion());
                p.setInt(5, arbo.getProvOfficeCode());
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
                    + "JOIN users u ON u.provOfficeCode=po.provOfficeCode";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provOfficeDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
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
                    + "WHERE a.arboRegion=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provOfficeDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
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

    public ArrayList<ARBO> getAllARBOsByProvince(int provinceID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBO> arboList = new ArrayList();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "WHERE a.provOfficeCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provOfficeDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
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
                    + "WHERE a.arboCityMun=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, cityMun);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provOfficeDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setAPCPQualified(rs.getInt("APCPQualified"));
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
                    + "WHERE a.provOfficeCode=? AND APCPQualified = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
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
                    + "WHERE a.provOfficeCode=? AND APCPQualified = 1";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
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

    public int getARBCount(int arboID) {
        int arboCount = 0;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        PreparedStatement p = null;
        try {
            con.setAutoCommit(false);
            String query = "SELECT COUNT(*) FROM arbs WHERE arboID =?;";
            p = con.prepareStatement(query);
            p.setInt(1, arboID);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                arboCount = rs.getInt("COUNT(*)");
            }
            rs.close();
            p.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arboCount;
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
}
