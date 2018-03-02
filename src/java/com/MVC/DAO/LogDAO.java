/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.Log;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class LogDAO {
    
    public void addLog(Log l) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`logs` (`actionType`, `actionBy`, `date`, `time`) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, l.getActionType());
            p.setInt(2, l.getActionBy());
            long s = System.currentTimeMillis();
            Date d = new Date(s);
            Time t = new Time(s);
            p.setDate(3, d);
            p.setTime(4, t);
            
            p.execute();

            p.close();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Log> retrieveAllLogs() {
        ArrayList<Log> list = new ArrayList<>();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.logs log join `dar-bms`.ref_actionType atype on log.actionType = atype.actionType  ORDER BY logID DESC";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Log temp = new Log();
                temp.setLogID(rs.getInt("logID"));
                temp.setActionType(rs.getInt("actionType"));
                temp.setActionBy(rs.getInt("actionBy"));
                temp.setDate(rs.getDate("date"));
                temp.setTime(rs.getTime("time"));
                temp.setActionDesc(rs.getString("actionDesc"));
                
                list.add(temp);
            }
            p.close();
            con.commit();
            con.close();
            return list;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(LogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
