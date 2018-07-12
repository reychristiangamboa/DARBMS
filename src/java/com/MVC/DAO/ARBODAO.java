/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.ARB;
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
public class ARBODAO {

    public ARBO getARBOByID(int id) {
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getArboID()== id){
                return arbo;
            }
        }
        return null;
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
                    + "`arboRegion`,`provOfficeCode`, `arboType`, `qualifiedSince`, `arboID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            p = con.prepareStatement(query);
            p.setString(1, arbo.getArboName());
            p.setInt(2, arbo.getArboCityMun());
            p.setInt(3, arbo.getArboProvince());
            p.setInt(4, arbo.getArboRegion());
            p.setInt(5, arbo.getProvOfficeCode());
            p.setInt(6, arbo.getArboType());
            p.setDate(7, arbo.getQualifiedSince());
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
        APCPRequestDAO rDAO = new APCPRequestDAO();
        ARBDAO aDAO = new ARBDAO();
        try {
            String query = "SELECT * FROM arbos a "
                    + "JOIN refcitymun c ON a.arboCityMun=c.citymunCode "
                    + "JOIN refprovince p ON c.provCode=p.provCode "
                    + "JOIN refregion r ON c.regCode=r.regCode "
                    + "JOIN ref_provoffice po ON a.provOfficeCode=po.provOfficeCode "
                    + "JOIN ref_arboType at ON a.arboType=at.arboType "
                    + "JOIN ref_arboStatus ast ON a.arboStatus=ast.arboStatus";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBO arbo = new ARBO();
                arbo.setArboID(rs.getInt("arboID"));
                arbo.setArboName(rs.getString("arboName"));
                arbo.setArboType(rs.getInt("arboType"));
                arbo.setArboTypeDesc(rs.getString("arboTypeDesc"));
                arbo.setArboStatus(rs.getInt("arboStatus"));
                arbo.setArboStatusDesc(rs.getString("arboStatusDesc"));
                arbo.setArboCityMun(rs.getInt("arboCityMun"));
                arbo.setArboCityMunDesc(rs.getString("cityMunDesc"));
                arbo.setArboProvince(rs.getInt("arboProvince"));
                arbo.setArboProvinceDesc(rs.getString("provDesc"));
                arbo.setArboRegion(rs.getInt("arboRegion"));
                arbo.setArboRegionDesc(rs.getString("regDesc"));
                arbo.setProvOfficeCode(rs.getInt("provOfficeCode"));
                arbo.setProvOfficeCodeDesc(rs.getString("provOfficeDesc"));
                arbo.setQualifiedSince(rs.getDate("qualifiedSince"));
                arbo.setDateOperational(rs.getDate("dateOperational"));
                arbo.setArbList(aDAO.getAllARBsARBO(rs.getInt("arboID")));
                arbo.setRequestList(rDAO.getAllARBORequests(rs.getInt("arboID")));
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
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getArboRegion() == regionID){
                arboList.add(arbo);
            }
        }
        
        return arboList;
    }

    public ArrayList<ARBO> getAllARBOsByProvince(int provinceOfficeCode) {
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getProvOfficeCode()== provinceOfficeCode){
                arboList.add(arbo);
            }
        }
        
        return arboList;
    }
    
    public ArrayList<ARBO> getAllARBOsByProvinceDesc(String provinceOfficeCode) {
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getProvOfficeCodeDesc().equals(provinceOfficeCode)){
                arboList.add(arbo);
            }
        }
        
        return arboList;
    }

    public ArrayList<ARBO> getAllARBOsByCityMun(int cityMun) {
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getArboCityMun()== cityMun){
                arboList.add(arbo);
            }
        }
        
        return arboList;
    }

    public ArrayList<ARBO> getAllNonQualifiedARBOs(int provinceID) {
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getProvOfficeCode()== provinceID && arbo.getQualifiedSince() == null){
                arboList.add(arbo);
            }
        }
        
        return arboList;
    }

    public ArrayList<ARBO> getAllQualifiedARBOs(int provinceID) {
        ArrayList<ARBO> allARBOs = getAllARBOs();
        ArrayList<ARBO> arboList = new ArrayList();
        
        for(ARBO arbo : allARBOs){
            if(arbo.getProvOfficeCode()== provinceID && arbo.getQualifiedSince() != null){
                arboList.add(arbo);
            }
        }
        
        return arboList;
    }

    public boolean updateARBOStatus(int arboID) {
        boolean success = false;
        PreparedStatement p = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE arbos SET `qualifiedSince`=? WHERE `arboID`=?";
            p = con.prepareStatement(query);
            Long l = System.currentTimeMillis();
            Date d = new Date(l);
            
            p.setDate(1, d);
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
    
    public ArrayList<ARBOType> getAllARBOTypes() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARBOType> typeList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_arboType r";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARBOType t = new ARBOType();
                t.setArboType(rs.getInt("arboType"));
                t.setArboTypeDesc(rs.getString("arboTypeDesc"));
                typeList.add(t);
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
        return typeList;
    }
    
    

}
