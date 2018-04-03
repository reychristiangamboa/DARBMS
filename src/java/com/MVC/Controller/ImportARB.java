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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            ARBDAO arbDAO = new ARBDAO();
            AddressDAO addressDAO = new AddressDAO();

            int arboID = Integer.parseInt(request.getParameter("arboID"));
            int count = 0;

            ARBODAO arboDAO = new ARBODAO();
            ARBO arbo = new ARBO();
            arbo = arboDAO.getARBOByID(arboID);

            ArrayList arbHolder = readExcelFile(request.getParameter("file"), 0);
            ArrayList dependentHolder = readExcelFile(request.getParameter("file"), 1);
            ArrayList cropHolder = readExcelFile(request.getParameter("file"), 2);

            ArrayList storeARB = new ArrayList();
            ArrayList storeDependent = new ArrayList();
            ArrayList storeCrop = new ArrayList();

            for (int i = 1; i < arbHolder.size(); i++) {
                storeARB = (ArrayList) arbHolder.get(i);
                ARB arb = new ARB();
                
                arb.setArboRepresentative(0);
                arb.setArboID(arboID);

                arb.setFirstName(storeARB.get(0).toString());
                arb.setMiddleName(storeARB.get(1).toString());
                arb.setLastName(storeARB.get(2).toString());
                arb.setGender(storeARB.get(3).toString());

                java.sql.Date memberSince = null;
                String excelDate = storeARB.get(4).toString(); // Parsing of Excel Date to Java Date
                String[] dateArr = excelDate.split("-");
                int memberSinceMonth = getValOfMonth(dateArr[1]);
                String finalMemberSince = dateArr[2] + "-" + memberSinceMonth + "-" + dateArr[0];
                try {
                    java.util.Date parsedDate = sdf.parse(finalMemberSince);
                    memberSince = new java.sql.Date(parsedDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(ImportARB.class.getName()).log(Level.SEVERE, null, ex);
                }
                arb.setMemberSince(memberSince);

                arb.setArbUnitNumStreet(storeARB.get(5).toString());
                arb.setRegCode(addressDAO.getRegCode(storeARB.get(9).toString()));
                arb.setProvCode(addressDAO.getProvCode(storeARB.get(8).toString(), arb.getRegCode()));
                arb.setCityMunCode(addressDAO.getCityMunCode(storeARB.get(7).toString(), arb.getProvCode(), arb.getRegCode()));
                arb.setBrgyCode(addressDAO.getBrgyCode(storeARB.get(6).toString(), arb.getCityMunCode(), arb.getProvCode(), arb.getRegCode()));
                arb.setEducationLevel(arbDAO.getEducationalLevel(storeARB.get(10).toString()));
                arb.setLandArea(Double.parseDouble(storeARB.get(11).toString()));
                
                

                int arbID = arbDAO.addARB(arb);

                ArrayList<Dependent> dependentList = new ArrayList();
                for (int j = 1; j < dependentHolder.size(); j++) {
                    storeDependent = (ArrayList) dependentHolder.get(j);
                    Dependent d = new Dependent();

                    if (arb.getFullName().equalsIgnoreCase(storeDependent.get(4).toString())) {
                        d.setName(storeDependent.get(0).toString());

                        java.sql.Date birthday = null;
                        String excelDate1 = storeDependent.get(1).toString(); // Parsing of Excel Date to Java Date
                        String[] dateArr1 = excelDate1.split("-");
                        int birthdayMonth = getValOfMonth(dateArr1[1]);
                        String finalBirthday = dateArr1[2] + "-" + birthdayMonth + "-" + dateArr1[0];
                        try {
                            java.util.Date parsedDate = sdf.parse(finalBirthday);
                            birthday = new java.sql.Date(parsedDate.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(ImportPastDueAccount.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        d.setBirthday(birthday);
                        d.setEducationLevel(arbDAO.getEducationalLevel(storeDependent.get(2).toString()));
                        d.setRelationshipType(arbDAO.getRelationshipType(storeDependent.get(3).toString()));

                        dependentList.add(d);
                    }
                }

                arbDAO.addDependents(arbID, dependentList);

                ArrayList<Crop> cropList = new ArrayList();
                for (int x = 1; x < cropHolder.size(); x++) {
                    storeCrop = (ArrayList) cropHolder.get(x);
                    Crop c = new Crop();

                    if (storeCrop.get(3).toString().equalsIgnoreCase(arb.getFullName())) {
                        c.setArbID(arbID);
                        c.setCropType(arbDAO.getCrop(storeCrop.get(0).toString()));

                        java.sql.Date startDate = null;
                        String excelDate2 = storeCrop.get(1).toString(); // Parsing of Excel Date to Java Date
                        String[] dateArr2 = excelDate2.split("-");
                        int startDateMonth = getValOfMonth(dateArr2[1]);
                        String finalStartDate = dateArr2[2] + "-" + startDateMonth + "-" + dateArr2[0];
                        try {
                            java.util.Date parsedDate = sdf.parse(finalStartDate);
                            startDate = new java.sql.Date(parsedDate.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(ImportARB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c.setStartDate(startDate);

                        java.sql.Date endDate = null;
                        String excelDate3 = storeCrop.get(2).toString(); // Parsing of Excel Date to Java Date
                        String[] dateArr3 = excelDate3.split("-");
                        int endDateMonth = getValOfMonth(dateArr3[1]);
                        String finalEndDate = dateArr3[2] + "-" + endDateMonth + "-" + dateArr3[0];
                        try {
                            java.util.Date parsedDate = sdf.parse(finalEndDate);
                            endDate = new java.sql.Date(parsedDate.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(ImportARB.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        c.setEndDate(endDate);
                        System.out.println(c.getStartDate());
                        System.out.println(c.getEndDate());
                    }
                    
                    cropList.add(c);
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

            OPCPackage pkg = OPCPackage.open(fileName);
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
