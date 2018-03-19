/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.ARB;
import com.MVC.Model.Crop;
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
public class CropDAO {

    public ArrayList<Crop> getAllCrops() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Crop> cropList = new ArrayList();
        try {
            String query = "SELECT * FROM ref_cropType;";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Crop c = new Crop();
                c.setCropType(rs.getInt("cropType"));
                c.setCropTypeDesc(rs.getString("cropTypeDesc"));
                cropList.add(c);
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CropDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CropDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cropList;
    }

    public ArrayList<Crop> getAllCropsByProvince(ArrayList<ARB> arbList) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Crop> cropList = new ArrayList();
        try {
            String query = "SELECT * FROM crops c "
                    + "JOIN ref_croptype t "
                    + "ON c.cropTag=t.cropType "
                    + "WHERE c.arbID=? GROUP BY c.cropTag, c.arbID ";

            for (ARB arb : arbList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, arb.getArbID());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Crop c = new Crop();
                    c.setArbID(rs.getInt("arbID"));
                    c.setCropType(rs.getInt("cropTag"));
                    c.setCropTypeDesc(rs.getString("cropTypeDesc"));
                    c.setStartDate(rs.getDate("startDate"));
                    c.setEndDate(rs.getDate("endDate"));
                    if(!checkIfCropExist(cropList, c)){
                        cropList.add(c);
                    }
                }
                pstmt.close();
                rs.close();
            }
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CropDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CropDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cropList;
    }

    public boolean checkIfCropExist(ArrayList<Crop> list, Crop c) {
        for(Crop C : list){
            if(c.getCropType() == C.getCropType()){
                return true;
            }
        }
        return false;
    }

    public double getCountOfCropsByMonth(Crop c, String d) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Crop> cropList = new ArrayList();
        double count = 0;
        try {
            String query = "SELECT * FROM crops "
                    + "WHERE `cropTag` = ? "
                    + "AND " + "'" + d + "'"
                    + " between `startDate` "
                    + "AND `endDate`;";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, c.getCropType());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                count++;
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CropDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(CropDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }
}
