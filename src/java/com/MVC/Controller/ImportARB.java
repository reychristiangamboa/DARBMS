/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.AddressDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.Crop;
import com.MVC.Model.Dependent;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Rey Christian
 */
public class ImportARB extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("import") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            ARBDAO arbDAO = new ARBDAO();
            AddressDAO addressDAO = new AddressDAO();

            int arboID = Integer.parseInt(request.getParameter("arboID"));

            ARBODAO arboDAO = new ARBODAO();
            ARBO arbo = new ARBO();
            arbo = arboDAO.getARBOByID(arboID);

            ArrayList arbHolder = readExcelFile(request.getParameter("file"), 0);
            ArrayList dependentHolder = readExcelFile(request.getParameter("file"), 1);
            ArrayList cropHolder = readExcelFile(request.getParameter("file"), 2);
            ArrayList cellStoreArrayList = new ArrayList();
            ArrayList cellStoreArrayList2 = new ArrayList();
            ArrayList cellStoreArrayList3 = new ArrayList();

            int count = 0;

            for (int i = 1; i < arbHolder.size(); i++) {

                cellStoreArrayList = (ArrayList) arbHolder.get(i);
                ARB arb = new ARB();

                arb.setArboID(arboID);

                arb.setArboRepresentative(0);

                arb.setFirstName(cellStoreArrayList.get(0).toString()); // First Name
                arb.setMiddleName(cellStoreArrayList.get(1).toString()); // Middle Name
                arb.setLastName(cellStoreArrayList.get(2).toString()); // Last Name
                arb.setGender(cellStoreArrayList.get(3).toString()); // Gender

                java.sql.Date memberSince = null;

                String excelDate = cellStoreArrayList.get(4).toString(); // Parsing of Excel Date to Java Date
                String[] dateArr = excelDate.split("-");

                int val = getValOfMonth(dateArr[1]);
                String finalDate = dateArr[2] + "-" + val + "-" + dateArr[0];

                try {
                    java.util.Date parsedMemberDate = sdf.parse(finalDate);
                    memberSince = new java.sql.Date(parsedMemberDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                }

                arb.setMemberSince(memberSince); // MemberSince

                arb.setArbUnitNumStreet(cellStoreArrayList.get(5).toString()); // Unit Num Street
                arb.setBrgyCode(addressDAO.getBrgyCode(cellStoreArrayList.get(6).toString())); // Barangay
                arb.setCityMunCode(addressDAO.getCityMunCode(cellStoreArrayList.get(7).toString())); // City
                arb.setProvCode(addressDAO.getProvCode(cellStoreArrayList.get(8).toString())); // Province
                arb.setRegCode(addressDAO.getRegCode(cellStoreArrayList.get(9).toString())); // Region

                int educationalLevel = arbDAO.getEducationalLevel(cellStoreArrayList.get(10).toString()); // Educational Level
                arb.setEducationLevel(educationalLevel);

                arb.setLandArea(Double.parseDouble(cellStoreArrayList.get(11).toString())); // Land Area

                int arbID = arbDAO.addARB(arb); // returns ID of newly added ARB

                ArrayList<Dependent> dependentList = new ArrayList();
                for (int x = 1; x < dependentHolder.size(); x++) {

                    cellStoreArrayList2 = (ArrayList) dependentHolder.get(x);
                    Dependent d = new Dependent();

                    if (cellStoreArrayList2.get(3).toString().equals(arb.getFullName())) { // Check if equal ARB Name
                        d.setName(cellStoreArrayList2.get(0).toString()); // Dependent Name

                        java.sql.Date birthday = null;

                        String excelDate2 = cellStoreArrayList2.get(1).toString(); // Parsing of Excel Date to Java Date
                        String[] dateArr2 = excelDate2.split("-");

                        int val2 = getValOfMonth(dateArr2[1]);
                        String finalDate2 = dateArr2[2] + "-" + val2 + "-" + dateArr2[0];

                        try {
                            java.util.Date parsedBirthday = sdf.parse(finalDate2);
                            birthday = new java.sql.Date(parsedBirthday.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        d.setBirthday(birthday); // Dependent Birthday

                        int educationalLevel2 = arbDAO.getEducationalLevel(cellStoreArrayList2.get(2).toString()); // Educational Level
                        d.setEducationLevel(educationalLevel2);

                        dependentList.add(d);
                    }

                }

                arbDAO.addDependents(arbID, dependentList);
                
                ArrayList<Crop> cropList = new ArrayList();
                for(int y = 1; y < cropHolder.size(); y++){
                    
                    cellStoreArrayList3 = (ArrayList) cropHolder.get(y);
                    Crop c = new Crop();
                    
                    if(cellStoreArrayList3.get(3).equals(arb.getFullName())){
                        c.setArbID(arb.getArbID());
                        
                        java.sql.Date startDate = null;

                        String excelDate3 = cellStoreArrayList3.get(1).toString(); // Parsing of Excel Date to Java Date
                        String[] dateArr3 = excelDate3.split("-");

                        int val3 = getValOfMonth(dateArr3[1]);
                        String finalDate3 = dateArr3[2] + "-" + val3 + "-" + dateArr3[0];

                        try {
                            java.util.Date parsedStartDate = sdf.parse(finalDate3);
                            startDate = new java.sql.Date(parsedStartDate.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        c.setStartDate(startDate);
                        
                        
                        java.sql.Date endDate = null;

                        String excelDate4 = cellStoreArrayList3.get(2).toString(); // Parsing of Excel Date to Java Date
                        String[] dateArr4 = excelDate4.split("-");

                        int val4 = getValOfMonth(dateArr4[1]);
                        String finalDate4 = dateArr4[2] + "-" + val4 + "-" + dateArr4[0];

                        try {
                            java.util.Date parsedEndDate = sdf.parse(finalDate4);
                            endDate = new java.sql.Date(parsedEndDate.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        c.setStartDate(endDate);
                        
                        c.setCropType(arbDAO.getCrop(cellStoreArrayList3.get(0).toString()));
                        
                    }
                }
                
                arbDAO.addCrops(arbID, cropList);
                
                count++;

            }

            int size = arbHolder.size() - 1;

            if (size == count) {
                request.setAttribute("success", "ARB imported!");
                request.setAttribute("arbo", arbo);
                request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in importing ARB. Try again.");
                request.setAttribute("arbo", arbo);
                request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
            }
        }

    }

    public static ArrayList readExcelFile(String fileName, int sheetIndex) {
        ArrayList cellArrayListHolder = new ArrayList();
        try {

            OPCPackage pkg = OPCPackage.open("C:\\Users\\Rey Christian\\Documents\\NetBeansProjects\\DAR-BMS\\" + fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

            Iterator rowIter = sheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow row = (XSSFRow) rowIter.next();
                Iterator cellIter = row.cellIterator();

                ArrayList cellStoreArrayList = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIter.next();
                    cellStoreArrayList.add(cell);
                }
                cellArrayListHolder.add(cellStoreArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellArrayListHolder;
    }

    public static int getValOfMonth(String month) {

        int val = 0;

        if (month.equals("Jan")) {
            val = 01;
        } else if (month.equals("Feb")) {
            val = 02;
        } else if (month.equals("Mar")) {
            val = 03;
        } else if (month.equals("Apr")) {
            val = 04;
        } else if (month.equals("May")) {
            val = 05;
        } else if (month.equals("Jun")) {
            val = 06;
        } else if (month.equals("Jul")) {
            val = 07;
        } else if (month.equals("Aug")) {
            val = 8;
        } else if (month.equals("Sep")) {
            val = 9;
        } else if (month.equals("Oct")) {
            val = 10;
        } else if (month.equals("Nov")) {
            val = 11;
        } else if (month.equals("Dec")) {
            val = 12;
        }

        return val;

    }
}
