/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.PastDueAccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
public class ImportPastDueAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        APCPRequestDAO dao = new APCPRequestDAO();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ArrayList pastDueAccountHolder = readExcelFile(request.getParameter("file"));
        ArrayList store = new ArrayList();

        for (int i = 1; i < pastDueAccountHolder.size(); i++) {
            store = (ArrayList) pastDueAccountHolder.get(i);
            PastDueAccount pda = new PastDueAccount();

            pda.setPastDueAmount(Double.parseDouble(store.get(0).toString()));

            int reason = capdevDAO.getPastDueReasonByDesc(store.get(1).toString());
            pda.setReasonPastDue(reason);

            if (store.get(2).toString().equalsIgnoreCase("N/A")) {
                pda.setOtherReason("N/A");
            } else {
                pda.setOtherReason(store.get(3).toString());
            }

            if (!store.get(3).toString().equalsIgnoreCase("N/A")) {
                java.sql.Date dateSettled = null;

                String excelDate = store.get(1).toString(); // Parsing of Excel Date to Java Date
                String[] dateArr = excelDate.split("-");

                int val = getValOfMonth(dateArr[1]);
                String finalDate = dateArr[2] + "-" + val + "-" + dateArr[0];

                try {
                    java.util.Date parsedDateSettled = sdf.parse(finalDate);
                    dateSettled = new java.sql.Date(parsedDateSettled.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(ImportPastDueAccount.class.getName()).log(Level.SEVERE, null, ex);
                }

                pda.setDateSettled(dateSettled);
            }

            pda.setRequestID(Integer.parseInt(request.getParameter("requestID")));
            pda.setRecordedBy((Integer) session.getAttribute("userID"));

            dao.addPastDueAccount(pda);

        }

        request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
        request.setAttribute("success", "Past Due Accounts successfully imported!");
        request.getRequestDispatcher("point-person-monitor-release.jsp").forward(request, response);

    }

    public static ArrayList readExcelFile(String file) {
        ArrayList cellArrayListHolder = new ArrayList();
        try {
            OPCPackage pkg = OPCPackage.open("C:\\Users\\Rey Christian\\Documents\\NetBeansProjects\\DARBMS\\" + file);
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
