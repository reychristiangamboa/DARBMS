/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.MVC.Model.Message;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Kobi
 */
public class MessageDAO {
    
    public int addMessage(Message m){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int n = 0;
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `messages` (`body`, `dateSent`, `timeSent`, `sentBy`) "
                    + "VALUES (?, ?, ?, ?);";
            
            long s = System.currentTimeMillis();
            Date d = new Date(s);
            Time t = new Time(s);
            
            PreparedStatement p = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            p.setString(1, m.getBody());
            p.setDate(2, d);
            p.setTime(3, t);
            p.setInt(4, m.getSentBy());
            
            
            p.executeUpdate();
            
            ResultSet rs = p.getGeneratedKeys();
            
            if (rs.next()) {
                n = rs.getInt(1);
                p.close();

            }
            
            p.close();
            con.commit();
            con.close();
            
            
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    
    public boolean sendMessage(int messageID, int userID){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `received_messages` (`messageID`,`userID`) "
                    + "VALUES (?, ?);";
            
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, messageID);
            p.setInt(2, userID);
            
            
            p.execute();
            
            p.close();
            con.commit();
            con.close();
            
            return true;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Message> retrieveMyMessages(int userID){
        ArrayList<Message> list = new ArrayList<>();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM received_messages rm JOIN messages m ON rm.messageID=m.messageID WHERE rm.userID=? AND rm.isRead='1' ORDER BY m.messageID DESC";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
                Message temp = new Message();
                temp.setMessageID(rs.getInt("messageID"));
                temp.setBody(rs.getString("body"));
                temp.setDateSent(rs.getDate("dateSent"));
                temp.setTimeSent(rs.getTime("timeSent"));
                temp.setSentBy(rs.getInt("sentBy"));
                temp.setSentTo(rs.getInt("userID"));
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
                Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getNumberOfUnreadMessages(int userID){
        
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "SELECT count(*) FROM received_messages WHERE `userID`=? AND `isRead`='0' ORDER BY messageID DESC";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            ResultSet rs = p.executeQuery();
            rs.next();
            
            int number = rs.getInt(1);
            
            p.close();
            con.commit();
            con.close();
            return number;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void readMyMessages(int userID){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `received_messages` SET `isRead`='1' WHERE `userID`=?;";
            PreparedStatement p = con.prepareStatement(query);
            p.setInt(1, userID);
            p.executeUpdate();
            
            p.close();
            con.commit();
            con.close();
            
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean addMessageFromSystem(Message m){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `messages` (`body`, `dateSent`, `timeSent`, `sentBy`, `sentTo`) "
                    + "VALUES (?, ?, ?, ?, ?);";
            
            long s = System.currentTimeMillis();
            Date d = new Date(s);
            Time t = new Time(s);
            
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, m.getBody());
            p.setDate(2, d);
            p.setTime(3, t);
            p.setInt(4, m.getSentBy());
            p.setInt(5, m.getSentTo());
            p.execute();
            
            p.close();
            con.commit();
            con.close();
            
            return true;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(MessageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
