/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.DAO;

import com.MVC.Database.DBConnectionFactory;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.Barangay;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.CityMun;
import com.MVC.Model.Province;
import com.MVC.Model.ProvincialBudget;
import com.MVC.Model.Region;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class AddressDAO {

    public ArrayList<Region> getAllRegions() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Region> regionList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.refregion";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Region r = new Region();
                r.setRegCode(rs.getInt("regCode"));
                r.setRegDesc(rs.getString("regDesc"));
                regionList.add(r);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return regionList;
    }

    public boolean addProvOffice(String branch, int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `ref_provOffice` (provOfficeDesc,regCode) VALUES (?,?);";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, branch);
            p.setInt(2, regCode);

            p.executeUpdate();

            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public boolean editProvOffice(String branch, int regCode, int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        boolean success = false;
        try {
            con.setAutoCommit(false);
            String query = "UPDATE `ref_provOffice` SET provOfficeDesc=?, regCode=? WHERE provOfficeCode=?";
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, branch);
            p.setInt(2, regCode);
            p.setInt(3, provOfficeCode);

            p.executeUpdate();

            p.close();
            con.commit();
            con.close();
            success = true;
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

    public ArrayList<Province> getAllProvOffices() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Province> provinceList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.ref_provOffice p JOIN refregion r ON p.regCode = r.regCode ";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Province p = new Province();
                p.setProvCode(rs.getInt("provOfficeCode"));
                p.setProvDesc(rs.getString("provOfficeDesc"));
                p.setRegCode(rs.getInt("regCode"));
                p.setRegDesc(rs.getString("regDesc"));
                provinceList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provinceList;
    }

    public Province getProvOffice(int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        Province p = new Province();

        try {
            String query = "SELECT * FROM `dar-bms`.ref_provOffice WHERE provOfficeCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provOfficeCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                p.setProvCode(rs.getInt("provOfficeCode"));
                p.setProvDesc(rs.getString("provOfficeDesc"));
                p.setRegCode(rs.getInt("regCode"));

                if (getAPCPBudgetByProvOffice(rs.getInt("provOfficeCode")) != null) {
                    p.setProvincialAPCPBudget(getAPCPBudgetByProvOffice(rs.getInt("provOfficeCode")));
                }

                if (getCAPDEVBudgetByProvOffice(rs.getInt("provOfficeCode")) != null) {
                    p.setProvincialCAPDEVBudget(getCAPDEVBudgetByProvOffice(rs.getInt("provOfficeCode")));
                }
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public CityMun getCityMun(int cityMunCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        CityMun c = new CityMun();

        try {
            String query = "SELECT * FROM `dar-bms`.refcitymun WHERE cityMunCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, cityMunCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                c.setCityMunCode(rs.getInt("cityMunCode"));
                c.setCityMunDesc(rs.getString("cityMunDesc"));
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public ArrayList<Province> getAllProvOfficesRegion(int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Province> provinceList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.ref_provOffice p JOIN refregion r ON p.regCode=r.regCode WHERE r.regCode = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Province p = new Province();
                p.setProvCode(rs.getInt("provOfficeCode"));
                p.setProvDesc(rs.getString("provOfficeDesc"));
                p.setRegCode(rs.getInt("regCode"));
                p.setRegDesc(rs.getString("regDesc"));
                provinceList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provinceList;
    }

    public ArrayList<Province> getAllProvOfficesRegion(String regDesc) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Province> provinceList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.ref_provOffice p join refregion r on p.regCode = r.regCode WHERE r. regDesc = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, regDesc);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Province p = new Province();
                p.setProvCode(rs.getInt("provOfficeCode"));
                p.setProvDesc(rs.getString("provOfficeDesc"));
                provinceList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provinceList;
    }

    public ArrayList<Province> getAllProvinces(int regionID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Province> provinceList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.refprovince WHERE regCode = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Province p = new Province();
                p.setProvCode(rs.getInt("provCode"));
                p.setProvDesc(rs.getString("provDesc"));
                provinceList.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return provinceList;
    }

    public ArrayList<Province> getAllProvOfficesMultipleReg(ArrayList<Integer> regionIDs) {
        ArrayList<Province> allProvOffices = getAllProvOffices();
        ArrayList<Province> provList = new ArrayList();

        for (Province p : allProvOffices) {
            for (int id : regionIDs) {
                if (p.getRegCode() == id) {
                    provList.add(p);
                }
            }
        }

        return provList;
    }

    public ArrayList<CityMun> getAllCityMuns(int provinceID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<CityMun> citymunList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.refcitymun WHERE provCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provinceID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CityMun cm = new CityMun();
                cm.setCityMunCode(rs.getInt("cityMunCode"));
                cm.setCityMunDesc(rs.getString("cityMunDesc"));
                citymunList.add(cm);
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citymunList;
    }

    public ArrayList<CityMun> getAllCityMuns() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<CityMun> citymunList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.refcitymun";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CityMun cm = new CityMun();
                cm.setCityMunCode(rs.getInt("cityMunCode"));
                cm.setCityMunDesc(rs.getString("cityMunDesc"));
                citymunList.add(cm);
            }
            pstmt.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citymunList;
    }

    public ArrayList<CityMun> getAllCityMunsByID(ArrayList<Integer> cityMunCodeList) {
        ArrayList<CityMun> allCityMun = getAllCityMuns();
        ArrayList<CityMun> list = new ArrayList();

        for (CityMun c : allCityMun) {
            for (int id : cityMunCodeList) {
                if (c.getCityMunCode() == id) {
                    list.add(c);
                }
            }
        }

        return list;
    }

    public ArrayList<Barangay> getAllBarangays(int cityID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Barangay> barangayList = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.refbrgy WHERE citymunCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, cityID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Barangay b = new Barangay();
                b.setBrgyCode(rs.getInt("brgyCode"));
                b.setBrgyDesc(rs.getString("brgyDesc"));
                barangayList.add(b);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return barangayList;
    }

    public int getBrgyCode(String brgyDesc, int cityMunCode, int provCode, int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.refbrgy WHERE `brgyDesc` LIKE ? AND cityMunCode=? AND provCode=? AND regCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, brgyDesc);
            pstmt.setInt(2, cityMunCode);
            pstmt.setInt(3, provCode);
            pstmt.setInt(4, regCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("brgyCode");
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

    public int getCityMunCode(String cityMunDesc, int provCode, int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.refcitymun WHERE `citymunDesc` LIKE ? AND provCode=? AND regCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, cityMunDesc);
            pstmt.setInt(2, provCode);
            pstmt.setInt(3, regCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("cityMunCode");
            }
            rs.close();
            pstmt.close();
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

    public int getProvCode(String provDesc, int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.refprovince WHERE `provDesc` LIKE ? AND regCode=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, provDesc);
            pstmt.setInt(2, regCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("provCode");
            }
            rs.close();
            pstmt.close();
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

    public String getRegDesc(int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        String desc = "";
        try {
            con.setAutoCommit(false);
            String query = "SELECT * FROM `dar-bms`.refregion WHERE `regCode`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                desc = rs.getString("regDesc");
            }
            pstmt.close();
            con.commit();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return desc;
    }

    public int getRegCode(String regionDesc) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.refregion WHERE `regDesc` LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, regionDesc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("regCode");
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

    //<editor-fold desc="CENTRAL">
    public ArrayList<ProvincialBudget> getAllAPCPBudget() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> list = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.APCPBudget "
                    + "WHERE approvedBy IS NULL AND isDisapproved = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setRegCode(rs.getInt("regCode"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                list.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<ProvincialBudget> getAllCAPDEVBudget() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> list = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.CAPDEVBudget "
                    + "WHERE approvedBy IS NULL AND isDisapproved = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                list.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void approveAPCPBudget(ProvincialBudget p) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "UPDATE `APCPBudget` SET `approvedBy`=?, `startDate`=?, `endDate`=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getApprovedBy());
            pstmt.setDate(2, p.getStartDate());
            pstmt.setDate(3, p.getEndDate());
            pstmt.setInt(4, p.getId());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approveCAPDEVBudget(ProvincialBudget p) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "UPDATE `provincialCAPDEVBudget` SET `approvedBy`=?, `startDate`=?, `endDate`=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getApprovedBy());
            pstmt.setDate(2, p.getStartDate());
            pstmt.setDate(3, p.getEndDate());
            pstmt.setInt(4, p.getId());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold desc="REGIONAL">
    public ArrayList<ProvincialBudget> getAllAPCPProvincialBudget(int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> list = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.APCPBudget "
                    + "WHERE approvedByRegional IS NULL AND regCode=? AND isDisapproved = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedByRegional(rs.getInt("approvedByRegional"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                list.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<ProvincialBudget> getAllCAPDEVProvincialBudget(int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> list = new ArrayList();

        try {
            String query = "SELECT * FROM `dar-bms`.CAPDEVBudget "
                    + "WHERE approvedByRegional IS NULL AND regCode = ? AND isDisapproved = 0";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                list.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<ProvincialBudget> getAPCPBudgetByRegion(int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> apcpBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT * FROM `dar-bms`.APCPBudget "
                    + "WHERE regCode = ? "
                    + "AND '" + d + "' BETWEEN startDate AND endDate "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regCode);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                apcpBudget.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apcpBudget;
    } // CURRENT DATE

    public double getCurrentAPCPBudgetReg(ArrayList<APCPRequest> requests, int regCode, int year) {

        double provBudgetDbl = 0;

        ArrayList<ProvincialBudget> provBudget = getAPCPBudgetByRegion(regCode);
        for (ProvincialBudget p : provBudget) {
            provBudgetDbl += p.getBudget();
        }

        APCPRequestDAO dao = new APCPRequestDAO();

        double releaseVal = dao.getYearlySumOfReleasesByRequest(requests, year);

        return provBudgetDbl - releaseVal;

    }

    public ArrayList<ProvincialBudget> getCAPDEVBudgetByRegion(int regCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> capdevBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT * FROM `dar-bms`.CAPDEVBudget "
                    + "WHERE regCode = ? "
                    + "AND '" + d + "' BETWEEN startDate AND endDate "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, regCode);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                capdevBudget.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return capdevBudget;
    } // CURRENT DATE

    public double getCurrentCAPDEVBudgetReg(ArrayList<CAPDEVPlan> plans, int regCode, int year) {

        double provBudgetDbl = 0;

        ArrayList<ProvincialBudget> provBudget = getCAPDEVBudgetByRegion(regCode);
        for (ProvincialBudget p : provBudget) {
            provBudgetDbl += p.getBudget();
        }

        CAPDEVDAO dao = new CAPDEVDAO();

        double implementedVal = dao.getYearlyImplementedBudget(plans, year);

        return provBudgetDbl - implementedVal;

    }

    public void approveProvincialAPCPBudget(ProvincialBudget p) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "UPDATE `APCPBudget` SET `approvedByRegional`=?, `startDate`=?, `endDate`=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getApprovedByRegional());
            pstmt.setDate(2, p.getStartDate());
            pstmt.setDate(3, p.getEndDate());
            pstmt.setInt(4, p.getId());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approveProvincialCAPDEVBudget(ProvincialBudget p) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "UPDATE `CAPDEVBudget` SET `approvedBy`=?, `startDate`=?, `endDate`=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getApprovedByRegional());
            pstmt.setDate(2, p.getStartDate());
            pstmt.setDate(3, p.getEndDate());
            pstmt.setInt(4, p.getId());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>

    //<editor-fold desc="PROVINCIAL">
    public ArrayList<ProvincialBudget> getAPCPBudgetByProvOffice(int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> apcpBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());
        System.out.println(d);

        try {
            String query = "SELECT * FROM `dar-bms`.APCPBudget "
                    + "WHERE provOfficeCode = ? "
                    + "AND '" + d + "' BETWEEN startDate AND endDate "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provOfficeCode);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                apcpBudget.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return apcpBudget;
    } // CURRENT DATE
    
    public double getSumAPCPBudgetProv(int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> apcpBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT SUM(budget) FROM `dar-bms`.APCPBudget "
                    + "WHERE '"+d+"' "
                    + "BETWEEN startDate AND endDate "
                    + "AND provOfficeCode=? "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provOfficeCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("SUM(budget)");
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    } // CURRENT DATE
    
    public double getSumCAPDEVBudgetProv(int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> apcpBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT SUM(budget) FROM `dar-bms`.CAPDEVBudget "
                    + "WHERE '"+d+"' "
                    + "BETWEEN startDate AND endDate "
                    + "AND provOfficeCode=? "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provOfficeCode);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("SUM(budget)");
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    } // CURRENT DATE

    public double getCurrentAPCPBudgetProv(ArrayList<APCPRequest> requests, int provOfficeCode, int year) {

        double provBudgetDbl = 0;

        ArrayList<ProvincialBudget> provBudget = getAPCPBudgetByProvOffice(provOfficeCode);
        for (ProvincialBudget p : provBudget) {
            provBudgetDbl += p.getBudget();
        }

        APCPRequestDAO dao = new APCPRequestDAO();

        double releaseVal = dao.getYearlySumOfReleasesByRequest(requests, year);

        return provBudgetDbl - releaseVal;

    }

    public ArrayList<ProvincialBudget> getCAPDEVBudgetByProvOffice(int provOfficeCode) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> capdevBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT * FROM `dar-bms`.CAPDEVBudget "
                    + "WHERE provOfficeCode = ? "
                    + "AND '" + d + "' BETWEEN startDate AND endDate "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, provOfficeCode);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProvincialBudget p = new ProvincialBudget();
                p.setId(rs.getInt("id"));
                p.setProvOfficeCode(rs.getInt("provOfficeCode"));
                p.setBudget(rs.getDouble("budget"));
                p.setRequestedBy(rs.getInt("requestedBy"));
                p.setApprovedBy(rs.getInt("approvedBy"));
                p.setReason(rs.getString("reason"));
                p.setStartDate(rs.getDate("startDate"));
                p.setEndDate(rs.getDate("endDate"));
                p.setIsDisapproved(rs.getBoolean("isDisapproved"));
                capdevBudget.add(p);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return capdevBudget;
    } // CURRENT DATE

    public double getCurrentCAPDEVBudgetProv(ArrayList<CAPDEVPlan> plans, int provOfficeCode, int year) {

        double provBudgetDbl = 0;

        ArrayList<ProvincialBudget> provBudget = getCAPDEVBudgetByProvOffice(provOfficeCode);
        for (ProvincialBudget p : provBudget) {
            provBudgetDbl += p.getBudget();
        }

        CAPDEVDAO dao = new CAPDEVDAO();

        double implementedVal = dao.getYearlyImplementedBudget(plans, year);

        return provBudgetDbl - implementedVal;

    }

    public void requestCAPDEVBudget(ProvincialBudget p) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "INSERT INTO `CAPDEVBudget` (`provOfficeCode`,`budget`, `requestedBy`, `reason`,`regCode`) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getProvOfficeCode());
            pstmt.setDouble(2, p.getBudget());
            pstmt.setInt(3, p.getRequestedBy());
            pstmt.setString(4, p.getReason());
            pstmt.setInt(5, p.getRegCode());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void requestAPCPBudget(ProvincialBudget p) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "INSERT INTO `APCPBudget` (`provOfficeCode`,`budget`, `requestedBy`, `reason`,`regCode`) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getProvOfficeCode());
            pstmt.setDouble(2, p.getBudget());
            pstmt.setInt(3, p.getRequestedBy());
            pstmt.setString(4, p.getReason());
            pstmt.setInt(5, p.getRegCode());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double getSumAPCPBudgetRegion(ArrayList<Province> provOfficeCodes) {

        double provBudgetDbl = 0;

        for (Province pro : provOfficeCodes) {
            ArrayList<ProvincialBudget> provBudget = getAPCPBudgetByProvOffice(pro.getProvCode());
            for (ProvincialBudget p : provBudget) {
                provBudgetDbl += p.getBudget();
            }
        }

        return provBudgetDbl;

    }
    
    public double getSumCAPDEVBudgetRegion(ArrayList<Province> provOfficeCodes) {

        double provBudgetDbl = 0;

        for (Province prov : provOfficeCodes) {
            ArrayList<ProvincialBudget> provBudget = getCAPDEVBudgetByProvOffice(prov.getProvCode());
            for (ProvincialBudget p : provBudget) {
                provBudgetDbl += p.getBudget();
            }
        }

        return provBudgetDbl;

    }
    
    public double getCurrentAPCPBudgetRegion(ArrayList<APCPRequest> requests, ArrayList<Province> provOfficeCodes, int year) {

        double provBudgetDbl = 0;

        for (Province pro : provOfficeCodes) {
            ArrayList<ProvincialBudget> provBudget = getAPCPBudgetByProvOffice(pro.getProvCode());
            for (ProvincialBudget p : provBudget) {
                provBudgetDbl += p.getBudget();
                System.out.println(provBudgetDbl);
            }
        }

        APCPRequestDAO dao = new APCPRequestDAO();

        double releaseVal = dao.getYearlySumOfReleasesByRequest(requests, year);
        System.out.println("RELEASE VAL:"+releaseVal);
        return provBudgetDbl - releaseVal;

    }

    public double getCurrentCAPDEVBudgetRegion(ArrayList<CAPDEVPlan> plans, ArrayList<Province> provOfficeCodes, int year) {

        double provBudgetDbl = 0;

        for (Province prov : provOfficeCodes) {
            ArrayList<ProvincialBudget> provBudget = getCAPDEVBudgetByProvOffice(prov.getProvCode());
            for (ProvincialBudget p : provBudget) {
                provBudgetDbl += p.getBudget();
            }
        }

        CAPDEVDAO dao = new CAPDEVDAO();

        double implementedVal = dao.getYearlyImplementedBudget(plans, year);

        return provBudgetDbl - implementedVal;

    }

    public double getSumAPCPBudget() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> apcpBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT SUM(budget) FROM `dar-bms`.APCPBudget "
                    + "WHERE '2018-01-01' "
                    + "BETWEEN startDate AND endDate "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("SUM(budget)");
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    } // CURRENT DATE

    public double getCurrentSumAPCPBudget(ArrayList<APCPRequest> requests, int year) {

        APCPRequestDAO dao = new APCPRequestDAO();

        double releaseVal = dao.getYearlySumOfReleasesByRequest(requests, year);

        return getSumAPCPBudget() - releaseVal;
    }

    public double getSumCAPDEVBudget() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        ArrayList<ProvincialBudget> apcpBudget = new ArrayList();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String d = f.format(cal.getTime());

        try {
            String query = "SELECT SUM(budget) FROM `dar-bms`.CAPDEVBudget "
                    + "WHERE '2018-01-01' "
                    + "BETWEEN startDate AND endDate "
                    + "AND approvedBy IS NOT NULL";
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("SUM(budget)");
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    } // CURRENT DATE

    public double getCurrentSumCAPDEVBudget(ArrayList<CAPDEVPlan> plans, int year) {

        CAPDEVDAO dao = new CAPDEVDAO();

        double implementedVal = dao.getYearlyImplementedBudget(plans, year);

        return getSumCAPDEVBudget() - implementedVal;
    }
    
    public double getFilteredSumAPCPBudget(ArrayList<Region> regions){
        double sum = 0;
        for(Region r : regions){
            ArrayList<Province> provOffices = getAllProvOfficesRegion(r.getRegCode());
            sum += getSumAPCPBudgetRegion(provOffices);
        }
        return sum;
    }
    
    public double getFilteredCurrentAPCPBudget(ArrayList<Region> regions, ArrayList<APCPRequest> released, int year){
        double sum = 0;
        for(Region r : regions){
            ArrayList<Province> provOffices = getAllProvOfficesRegion(r.getRegCode());
            sum += getSumAPCPBudgetRegion(provOffices);
        }
        APCPRequestDAO dao = new APCPRequestDAO();
        double releaseVal = dao.getYearlySumOfReleasesByRequest(released, year);
        return sum - releaseVal;
    }
    
    public double getFilteredSumCAPDEVBudget(ArrayList<Region> regions){
        double sum = 0;
        for(Region r : regions){
            ArrayList<Province> provOffices = getAllProvOfficesRegion(r.getRegCode());
            sum += getSumCAPDEVBudgetRegion(provOffices);
        }
        return sum;
    }
    
    public double getFilteredCurrentCAPDEVBudget(ArrayList<Region> regions, ArrayList<CAPDEVPlan> plans, int year){
        double sum = 0;
        for(Region r : regions){
            ArrayList<Province> provOffices = getAllProvOfficesRegion(r.getRegCode());
            sum += getSumCAPDEVBudgetRegion(provOffices);
        }
        CAPDEVDAO dao = new CAPDEVDAO();

        double implementedVal = dao.getYearlyImplementedBudget(plans, year);
        return sum - implementedVal;
    }
    //</editor-fold>

    public void disapproveAPCPBudget(int id) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "UPDATE `APCPBudget` SET `isDisapproved=1` WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disapproveCAPDEVBudget(int id) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "UPDATE `CAPDEVBudget` SET `isDisapproved=1` WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(AddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
