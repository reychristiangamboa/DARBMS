/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.EvaluationDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.Evaluation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Rey Christian
 */
public class ImportLINKSFARMEvaluation extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession session = request.getSession();
        EvaluationDAO dao = new EvaluationDAO();
        ARBDAO dao2 = new ARBDAO();

        ArrayList<Evaluation> evaluations = new ArrayList();

        ARB arb = dao2.getARBByID(Integer.parseInt(request.getParameter("id")));

        int arbID = Integer.parseInt(request.getParameter("id"));

        ArrayList evaluationHolder = readExcelFile(request.getParameter("file"));
        ArrayList storeEvaluation = new ArrayList();

        for (int i = 0; i < evaluationHolder.size(); i++) {
            storeEvaluation = (ArrayList) evaluationHolder.get(i);
            Evaluation e = new Evaluation();

            e.setEvaluatedBy((Integer) session.getAttribute("userID"));
            e.setArbID(arbID);
            e.setEvaluationType(4);
            e.setEvaluationDTN(storeEvaluation.get(0).toString());
            e.setRating(Double.parseDouble(storeEvaluation.get(1).toString()));

            java.sql.Date evalDate = null;
            String excelDate = storeEvaluation.get(2).toString(); // Parsing of Excel Date to Java Date
            String[] dateArr = excelDate.split("-");
            int evalDateMonth = getValOfMonth(dateArr[1]);
            String finalEvalDate = dateArr[2] + "-" + evalDateMonth + "-" + dateArr[0];
            try {
                java.util.Date parsedDate = sdf.parse(finalEvalDate);
                evalDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ImportLINKSFARMEvaluation.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.setEvaluationDate(evalDate);

            java.sql.Date evalStartDate = null;
            String excelDate1 = storeEvaluation.get(3).toString(); // Parsing of Excel Date to Java Date
            String[] dateArr1 = excelDate1.split("-");
            int evalStartDateMonth = getValOfMonth(dateArr1[1]);
            String finalEvalStartDate = dateArr1[2] + "-" + evalStartDateMonth + "-" + dateArr1[0];
            try {
                java.util.Date parsedDate = sdf.parse(finalEvalStartDate);
                evalStartDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ImportLINKSFARMEvaluation.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.setEvaluationStartDate(evalStartDate);

            java.sql.Date evalEndDate = null;
            String excelDate2 = storeEvaluation.get(4).toString(); // Parsing of Excel Date to Java Date
            String[] dateArr2 = excelDate2.split("-");
            int evalEndDateMonth = getValOfMonth(dateArr2[1]);
            String finalEvalEndDate = dateArr2[2] + "-" + evalEndDateMonth + "-" + dateArr2[0];
            try {
                java.util.Date parsedDate = sdf.parse(finalEvalEndDate);
                evalEndDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ImportLINKSFARMEvaluation.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.setEvaluationEndDate(evalEndDate);

            evaluations.add(e);

        }

        for (Evaluation e : evaluations) {
            dao.addLINKSFARMEvaluation(e);
        }

        request.setAttribute("success", "LINKSFARM evaluation successfully imported!");
        request.setAttribute("arb", arb);
        request.getRequestDispatcher("arb-profile.jsp").forward(request, response);

    }

    public static ArrayList readExcelFile(String file) {
        ArrayList cellArrayListHolder = new ArrayList();
        try {
            OPCPackage pkg = OPCPackage.open(file);
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            XSSFSheet sheet = workbook.getSheetAt(0);

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
