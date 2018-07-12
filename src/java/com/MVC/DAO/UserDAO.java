/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.User;
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
public class UserDAO {

    public User findUser(String email) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        User u = new User();
        try {
            String query = "SELECT * FROM `dar-bms`.users u "
                    + "JOIN ref_userType t ON u.userType=t.userType "
                    + "JOIN ref_provOffice p ON u.provOfficeCode=p.provOfficeCode "
                    + "JOIN refregion r ON u.regOfficeCode=r.regCode "
                    + "WHERE `email`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                u.setUserID(rs.getInt("userID"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("fullName"));
                u.setAddress(rs.getString("address"));
                u.setContactNo(rs.getString("contactNo"));
                u.setUserType(rs.getInt("userType"));
                u.setUserTypeDesc(rs.getString("userTypeDesc"));
                u.setActive(rs.getInt("active"));
                u.setProvOfficeCode(rs.getInt("provOfficeCode"));
                u.setProvOfficeDesc(rs.getString("provOfficeDesc"));
                u.setRegOfficeCode(rs.getInt("regOfficeCode"));
                u.setRegDesc(rs.getString("regDesc"));
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
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public boolean loginUser(User u) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM users WHERE `email` = ? AND `password` = sha2(?,224)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, u.getEmail());
            pstmt.setString(2, u.getPassword());
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                success = true;
            }
            pstmt.close();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public User searchUser(int userID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        User u = new User();
        try {
            String query = "SELECT * FROM users u JOIN ref_userType t ON u.userType=t.userType WHERE userID=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                u.setUserID(rs.getInt("userID"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("fullName"));
                u.setAddress(rs.getString("address"));
                u.setContactNo(rs.getString("contactNo"));
                u.setUserType(rs.getInt("userType"));
                u.setUserTypeDesc(rs.getString("userTypeDesc"));
                u.setActive(rs.getInt("active"));
                u.setProvOfficeCode(rs.getInt("provOfficeCode"));
                u.setRegOfficeCode(rs.getInt("regOfficeCode"));
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
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            String query = "SELECT * FROM users u JOIN ref_userType t ON u.userType=t.userType";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setAddress(rs.getString("address"));
                u.setContactNo(rs.getString("contactNo"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setUserType(rs.getInt("userType"));
                u.setUserTypeDesc(rs.getString("userTypeDesc"));
                u.setActive(rs.getInt("active"));
                u.setProvOfficeCode(rs.getInt("provOfficeCode"));
                u.setRegOfficeCode(rs.getInt("regOfficeCode"));
                userList.add(u);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    public ArrayList<User> getAllPointPersonProvince(int provOfficeCode) {
        ArrayList<User> userList = new ArrayList();
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        try {
            String query = "SELECT * FROM users u JOIN ref_userType t ON u.userType=t.userType WHERE u.userType=8 AND u.provOfficeCode=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, provOfficeCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setAddress(rs.getString("address"));
                u.setContactNo(rs.getString("contactNo"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setUserType(rs.getInt("userType"));
                u.setUserTypeDesc(rs.getString("userTypeDesc"));
                u.setActive(rs.getInt("active"));
                u.setProvOfficeCode(rs.getInt("provOfficeCode"));
                u.setRegOfficeCode(rs.getInt("regOfficeCode"));
                userList.add(u);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    public boolean addPersonnel(User u) throws SQLException {
        boolean success = false;
        PreparedStatement pstmt = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`users` (`email`, `password`, `fullName`,"
                    + " `address`, `contactNo`, `userType`,`provOfficeCode`,`regOfficeCode`) VALUES "
                    + "(?, sha2(?,224), ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, u.getEmail());
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getFullName());
            pstmt.setString(4, u.getAddress());
            pstmt.setString(5, u.getContactNo());
            pstmt.setInt(6, u.getUserType());
            pstmt.setInt(7, u.getProvOfficeCode());
            pstmt.setInt(8, u.getRegOfficeCode());

            pstmt.executeUpdate();
            pstmt.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean editPersonalInformation(User u, int userID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        PreparedStatement p = null;
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`users` SET `fullName`=?, `address`=?, `contactNo`=? WHERE `userID`=?;";
            p = con.prepareStatement(query);
            p.setString(1, u.getFullName());
            p.setString(2, u.getAddress());
            p.setString(3, u.getContactNo());
            p.setInt(4, userID);
            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean editAccount(User u, int userID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        PreparedStatement p = null;
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `dar-bms`.`users` SET `email`=?, `password`= sha2(?,224) WHERE `userID`=?;";
            p = con.prepareStatement(query);
            p.setString(1, u.getEmail());
            p.setString(2, u.getPassword());
            p.setInt(3, userID);
            p.executeUpdate();
            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public void activateAccount(String[] userList) throws SQLException {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        PreparedStatement p = null;

        try {
            con.setAutoCommit(false);
            String query = "UPDATE users SET active='1' WHERE userID = ?";
            p = con.prepareStatement(query);

            for (int i = 0; i < userList.length; i++) {
                p.setInt(1, Integer.parseInt(userList[i]));
                p.addBatch();
            }
            int[] affectedRecords = p.executeBatch();
            con.commit();
            //you can validate rows before commit, upto your requirement
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (p != null) {
                p.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void deactivateAccount(String[] userList) throws SQLException {

        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        PreparedStatement p = null;

        try {
            con.setAutoCommit(false);
            String query = "UPDATE users SET active='0' WHERE userID = ?";
            p = con.prepareStatement(query);

            for (int i = 0; i < userList.length; i++) {
                p.setInt(1, Integer.parseInt(userList[i]));
                p.addBatch();
            }
            int[] affectedRecords = p.executeBatch();
            con.commit();
            //you can validate rows before commit, upto your requirement
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (p != null) {
                p.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public ArrayList<User> retrieveAllActiveUsers() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<User> list = new ArrayList();
        try {
            String query = "SELECT * FROM users u JOIN ref_usertype type ON u.userType = type.userType WHERE active='1'";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setAddress(rs.getString("address"));
                u.setContactNo(rs.getString("contactNo"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setUserType(rs.getInt("userType"));
                u.setUserTypeDesc(rs.getString("userTypeDesc"));
                u.setActive(rs.getInt("active"));
                list.add(u);
            }
            rs.close();
            p.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<User> retrieveAllInactiveUsers() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<User> list = new ArrayList();
        try {
            String query = "SELECT * FROM users u JOIN ref_usertype type ON u.userType = type.userType WHERE active='0'";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setFullName(rs.getString("fullName"));
                u.setAddress(rs.getString("address"));
                u.setContactNo(rs.getString("contactNo"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setUserType(rs.getInt("userType"));
                u.setUserTypeDesc(rs.getString("userTypeDesc"));
                u.setActive(rs.getInt("active"));
                list.add(u);
            }
            rs.close();
            p.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
