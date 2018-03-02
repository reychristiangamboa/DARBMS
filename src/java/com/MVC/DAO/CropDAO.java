/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.Crop;
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
}
