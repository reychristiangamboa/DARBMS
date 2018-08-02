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
import com.MVC.Model.EducationLevel;
import com.MVC.Model.RelationshipType;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class ARBDAO {

    public ARB getARBByID(int arbID) {
        
        APCPRequestDAO dao = new APCPRequestDAO();
        
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
                arb.setIsCOMAT(rs.getInt("isCOMAT"));
                arb.setFirstName(rs.getString("firstName"));
                arb.setMiddleName(rs.getString("middleName"));
                arb.setLastName(rs.getString("lastName"));
                arb.setTIN(rs.getInt("TIN"));
                arb.setBirthday(rs.getDate("birthday"));
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
                arb.setCurrentCrops(getAllARBCurrentCrops(rs.getInt("arbID")));
                arb.setCrops(getAllARBCrops(rs.getInt("arbID")));
                arb.setDependents(getAllARBDependents(rs.getInt("arbID")));
                arb.setArbStatus(rs.getInt("arbStatus"));
                arb.setArbStatusDesc(rs.getString("arbStatusDesc"));
                arb.setClusterID(rs.getInt("clusterID"));
                
                //arb.setRepayments(dao.getArbRepaymentsByARB(rs.getInt("arbID")));
                //arb.setDisbursements(dao.getAllDisbursementsByARB(rs.getInt("arbID")));
                
                //arb.setNonARB(rs.getBoolean("nonARB"));
            } else {
                return null;
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
        return arb;
    }

    public ArrayList<ARB> getAllARBsARBO(int arboID) {
        ArrayList<ARB> allARBs = getAllARBs();
        ArrayList<ARB> list = new ArrayList();

        for (ARB arb : allARBs) {
            if (arb.getArboID() == arboID) {
                list.add(arb);
            }
        }

        return list;
    }

    public ArrayList<ARB> getAllARBsOfARBOs(ArrayList<ARBO> arboList) {
        ArrayList<ARB> allARBs = getAllARBs();
        ArrayList<ARB> list = new ArrayList();

        for (ARB arb : allARBs) {
            for (ARBO arbo : arboList) {
                if (arb.getArboID() == arbo.getArboID()) {
                    list.add(arb);
                }
            }
        }

        return list;
    }

    public ArrayList<ARB> getAllARBs() {
        
        APCPRequestDAO dao = new APCPRequestDAO();
        
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
                    + "JOIN `dar-bms`.refregion r ON a.regCode=r.regCode ";

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ARB arb = new ARB();
                arb.setArbID(rs.getInt("arbID"));
                arb.setArboID(rs.getInt("arboID"));
                arb.setIsCOMAT(rs.getInt("isCOMAT"));
                arb.setFirstName(rs.getString("firstName"));
                arb.setMiddleName(rs.getString("middleName"));
                arb.setLastName(rs.getString("lastName"));
                arb.setTIN(rs.getInt("TIN"));
                arb.setBirthday(rs.getDate("birthday"));
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
                arb.setCurrentCrops(getAllARBCurrentCrops(rs.getInt("arbID")));
                arb.setDependents(getAllARBDependents(rs.getInt("arbID")));
                arb.setArbStatus(rs.getInt("arbStatus"));
                arb.setArbStatusDesc(rs.getString("arbStatusDesc"));
                arb.setClusterID(rs.getInt("clusterID"));
                
                //arb.setRepayments(dao.getArbRepaymentsByARB(rs.getInt("arbID")));
                //arb.setDisbursements(dao.getAllDisbursementsByARB(rs.getInt("arbID")));
                
                //arb.setNonARB(rs.getBoolean("nonARB"));
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

    public ArrayList<ARB> getAllRegionalARBs(int regCode) {
        
        ARBODAO arboDAO = new ARBODAO();
        //ArrayList<ARB> allARBs = getAllARBs();
        ArrayList<ARB> list = new ArrayList();
        ArrayList<ARBO> arbos = arboDAO.getAllARBOs();
        
        for(ARBO arbo : arbos){ // runs through all ARBO
            if(arbo.getArboRegion() == regCode){ // if ARBO is part of that region
                ArrayList<ARB> arbList = getAllARBsARBO(arbo.getArboID()); // get all ARB
                for(ARB arb : arbList){
                    list.add(arb);
                }
            }
        }

        return list;
    }

    public ArrayList<ARB> getAllProvincialARBs(int provOfficeCode) {
        ARBODAO arboDAO = new ARBODAO();
        //ArrayList<ARB> allARBs = getAllARBs();
        ArrayList<ARB> list = new ArrayList();
        ArrayList<ARBO> arbos = arboDAO.getAllARBOs();
        
        for(ARBO arbo : arbos){ // runs through all ARBO
            if(arbo.getProvOfficeCode() == provOfficeCode){ // if ARBO is part of that provOffice
                ArrayList<ARB> arbList = getAllARBsARBO(arbo.getArboID()); // get all ARB
                for(ARB arb : arbList){
                    list.add(arb);
                }
            }
        }

        return list;
    }

    public ArrayList<Crop> getAllARBCurrentCrops(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Crop> cropList = new ArrayList();
        CropDAO cDAO = new CropDAO();
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Long l = System.currentTimeMillis();
            Date d = new Date(l);
            String dStr = sdf.format(d);

            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.crops c ON a.arbID=c.arbID "
                    + "JOIN `dar-bms`.ref_cropType ct ON c.cropTag=ct.cropType "
                    + "WHERE (a.arbID=?) AND ('" + dStr + "' BETWEEN c.startDate AND c.endDate)";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Crop c = new Crop();
                c.setStartDate(rs.getDate("startDate"));
                c.setEndDate(rs.getDate("endDate"));
                c.setCropType(rs.getInt("cropTag"));
                c.setCropTypeDesc(rs.getString("cropTypeDesc"));

                if (!cDAO.checkIfCropExist(cropList, c)) {
                    cropList.add(c);
                }

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
        return cropList;
    }

    public ArrayList<Crop> getAllARBCrops(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Crop> cropList = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.crops c ON a.arbID=c.arbID "
                    + "JOIN `dar-bms`.ref_cropType ct ON c.cropTag=ct.cropType "
                    + "WHERE a.arbID=? ORDER BY c.startDate";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arbID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Crop c = new Crop();
                c.setStartDate(rs.getDate("startDate"));
                c.setEndDate(rs.getDate("endDate"));
                c.setCropType(rs.getInt("cropTag"));
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
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cropList;
    }

    public ArrayList<Dependent> getAllARBDependents(int arbID) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Dependent> dependentList = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.arbs a "
                    + "JOIN `dar-bms`.dependents d ON a.arbID=d.arbID "
                    + "JOIN `dar-bms`.ref_educationLevel e ON d.educationLevel=e.educationLevel "
                    + "JOIN `dar-bms`.ref_relationshipType r ON d.relationshipType=r.relationshipType "
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
                d.setRelationshipType(rs.getInt("relationshipType"));
                d.setRelationshipTypeDesc(rs.getString("relationshipTypeDesc"));
                dependentList.add(d);
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
        return dependentList;
    }

    public boolean addARB(ARB arb) {
        PreparedStatement pstmt = null;
        Connection con = null;
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`arbs` (`arboID`, `isCOMAT`, `firstName`, `middleName`, "
                    + "`lastName`, `memberSince`,`arbUnitNumStreet`,`brgyCode`,`cityMunCode`,`provCode`,`regCode`, "
                    + "`gender`, `educationLevel`, `landArea`,`TIN`,`arbID`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, arb.getArboID());
            pstmt.setInt(2, arb.getIsCOMAT());
            pstmt.setString(3, arb.getFirstName());
            pstmt.setString(4, arb.getMiddleName());
            pstmt.setString(5, arb.getLastName());
            pstmt.setDate(6, arb.getMemberSince());
            pstmt.setString(7, arb.getArbUnitNumStreet());
            pstmt.setInt(8, arb.getBrgyCode());
            pstmt.setInt(9, arb.getCityMunCode());
            pstmt.setInt(10, arb.getProvCode());
            pstmt.setInt(11, arb.getRegCode());
            pstmt.setString(12, arb.getGender());
            pstmt.setInt(13, arb.getEducationLevel());
            pstmt.setDouble(14, arb.getLandArea());
            pstmt.setInt(15, arb.getTIN());
            pstmt.setInt(16, arb.getArbID());
            pstmt.executeUpdate();

            pstmt.close();
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

    public boolean addCrops(int arbID, ArrayList<Crop> cropList) {
        boolean success = false;
        PreparedStatement pstmt = null;
        Connection con = null;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        con = myFactory.getConnection();
        try {
            con.setAutoCommit(false);
            String query = "INSERT INTO `dar-bms`.`crops` (`arbID`, `cropTag`,`startDate`,`endDate`) VALUES (?, ?, ?, ?);";

            for (Crop crop : cropList) {
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, arbID);
                pstmt.setInt(2, crop.getCropType());
                pstmt.setDate(3, crop.getStartDate());
                pstmt.setDate(4, crop.getEndDate());
                pstmt.executeUpdate();
                pstmt.close();
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
            String query = "INSERT INTO `dar-bms`.`dependents` (`arbID`, `name`, `birthday`, `educationLevel`, `relationshipType`) VALUES (?, ?, ?, ?, ?);";

            for (Dependent d : dependentList) {
                p = con.prepareStatement(query);
                p.setInt(1, arbID);
                p.setString(2, d.getName());
                p.setDate(3, d.getBirthday());
                p.setInt(4, d.getEducationLevel());
                p.setInt(5, d.getRelationshipType());
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

    public int getRelationshipType(String relationshipType) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        int id = 0;
        try {
            String query = "SELECT * FROM `dar-bms`.ref_relationshipType WHERE `relationshipTypeDesc`=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, relationshipType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("relationshipType");
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

    public ArrayList<ARB> getAllARBsByCityMun(int cityMun) {
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
                    + "WHERE a.cityMunCode=?;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, cityMun);
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

    public ArrayList<ARB> getAllARBsByCityMun(int cityMun, int arboID) {
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
                    + "WHERE a.cityMunCode=? AND a.arboID=?;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, cityMun);
            pstmt.setInt(2, arboID);
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
                arb.setCurrentCrops(getAllARBCurrentCrops(rs.getInt("arbID")));
                arb.setCrops(getAllARBCrops(rs.getInt("arbID")));
                arb.setDependents(getAllARBDependents(rs.getInt("arbID")));
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

    public ArrayList<Integer> getARBCityMun(ArrayList<ARB> arbList) {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<Integer> arbProvList = new ArrayList();
        int cityMunCode = 0;
        try {
            String query = "SELECT cityMunCode FROM `dar-bms`.arbs  WHERE arbID=?;";
            for (ARB a : arbList) {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, a.getArbID());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    cityMunCode = rs.getInt("cityMunCode");
                    if (!checkIfCityMunExist(arbProvList, cityMunCode)) {
                        arbProvList.add(cityMunCode);
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
                Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBODAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arbProvList;
    }

    public boolean checkIfCityMunExist(ArrayList<Integer> list, int c) {
        for (int C : list) {
            if (C == c) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfARBExists(ARB arb1) {
        boolean success = false;
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();

        try {
            String query = "SELECT * FROM arbs";
            PreparedStatement p = con.prepareStatement(query);
            ResultSet rs = p.executeQuery();
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
                arb.setCityMunCode(rs.getInt("cityMunCode"));
                arb.setProvCode(rs.getInt("provCode"));
                arb.setRegCode(rs.getInt("regCode"));
                arb.setGender(rs.getString("gender"));
                arb.setEducationLevel(rs.getInt("educationLevel"));
                arb.setLandArea(rs.getDouble("landArea"));
                arb.setCurrentCrops(getAllARBCurrentCrops(rs.getInt("arbID")));
                arb.setCrops(getAllARBCrops(rs.getInt("arbID")));
                arb.setDependents(getAllARBDependents(rs.getInt("arbID")));

                if (arb1.toString().equalsIgnoreCase(arb.toString())) {
                    success = true;
                }

            }
            rs.close();
            p.close();
            con.close();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }

    public ArrayList<EducationLevel> getAllEducationLevel() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<EducationLevel> educations = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.ref_educationLevel";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                EducationLevel e = new EducationLevel();
                e.setEducationLevel(rs.getInt("educationLevel"));
                e.setEducationLevelDesc(rs.getString("educationLevelDesc"));
                educations.add(e);
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
        return educations;
    }

    public ArrayList<RelationshipType> getAllRelationshipType() {
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<RelationshipType> type = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.ref_relationshiptype";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                RelationshipType e = new RelationshipType();
                e.setRelationshipType(rs.getInt("relationshipType"));
                e.setRelationshipTypeDesc(rs.getString("relationshipTypeDesc"));
                type.add(e);
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
        return type;
    }
    
    public ArrayList<ARB> getAllAPCPRecipients(){
        DBConnectionFactory myFactory = DBConnectionFactory.getInstance();
        Connection con = myFactory.getConnection();
        ArrayList<ARB> arbList = new ArrayList();
        try {
            String query = "SELECT * FROM `dar-bms`.apcp_recipients rec "
                    + "JOIN arbs a on rec.arbID = a.arbID "
                    + "JOIN `dar-bms`.ref_educationLevel e ON a.educationLevel=e.educationLevel "
                    + "JOIN `dar-bms`.ref_arbStatus s ON a.arbStatus=s.arbStatus "
                    + "JOIN `dar-bms`.refbrgy b ON a.brgyCode=b.brgyCode "
                    + "JOIN `dar-bms`.refcitymun c ON a.cityMunCode=c.citymunCode "
                    + "JOIN `dar-bms`.refprovince p ON a.provCode=p.provCode "
                    + "JOIN `dar-bms`.refregion r ON a.regCode=r.regCode "
                    + "JOIN apcp_requests req on rec.requestID = req.requestID "
                    + "WHERE req.requestStatus = 4 OR req.requestStatus = 5 OR req.requestStatus = 6 "
                    + "GROUP BY a.arbID";
            PreparedStatement pstmt = con.prepareStatement(query);
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
                Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(ARBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arbList;
    }

}
