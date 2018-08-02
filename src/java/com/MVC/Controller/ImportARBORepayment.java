/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.Repayment;
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
public class ImportARBORepayment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList repaymentHolder = readExcelFile(request.getParameter("file"));
        ArrayList cellStoreArrayList = new ArrayList();

        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRequest req = dao.getRequestByID(Integer.parseInt(request.getParameter("requestID")));
        
        if (repaymentsExceedLimit(req, repaymentHolder)) {
            request.setAttribute("errMessage", "RELEASE/S amount (Php " + sumRepayments(repaymentHolder) + ") exceeds O/S Balance (Php " + req.getTotalReleaseOSBalance() + "). Try again.");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        } else {
            for (int i = 1; i < repaymentHolder.size(); i++) {
                cellStoreArrayList = (ArrayList) repaymentHolder.get(i);

                Repayment r = new Repayment();

                r.setRequestID(Integer.parseInt(request.getParameter("requestID")));
                r.setAmount(Double.parseDouble(cellStoreArrayList.get(0).toString())); // REPAYMENT AMOUNT
                
                
                java.sql.Date repaymentDate = null;
                String excelDate = cellStoreArrayList.get(1).toString(); // REPAYMENT DATE  // Parsing of Excel Date to Java Date
                
                String[] dateArr = excelDate.split("-");

                int val = getValOfMonth(dateArr[1]);
                String finalDate = dateArr[2] + "-" + val + "-" + dateArr[0];
                

                try {
                    java.util.Date parsedReleaseDate = sdf.parse(finalDate);
                    repaymentDate = new java.sql.Date(parsedReleaseDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(ImportRelease.class.getName()).log(Level.SEVERE, null, ex);
                }

                r.setDateRepayment(repaymentDate);
                r.setRecordedBy((Integer) session.getAttribute("userID")); // RELEASED BY

                

                dao.addARBORepayment(r);

            }

            request.setAttribute("success", "ARBO Repayments successfully imported!");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        }
        
    }
    
    public static boolean repaymentsExceedLimit(APCPRequest req, ArrayList repaymentHolder) {
        ArrayList store = new ArrayList();
        double limit = 0;
        double sumARBORepayments = 0;
        double finalLimit = 0;
        APCPRequestDAO dao = new APCPRequestDAO();

        for (int i = 1; i < repaymentHolder.size(); i++) {
            store = (ArrayList) repaymentHolder.get(i);
            limit += Double.parseDouble(store.get(0).toString());
        }

        req.setArboRepayments(dao.getAllARBORepaymentsByRequest(req.getRequestID()));
        for (Repayment rep : req.getArboRepayments()) {
            limit += rep.getAmount();
        }
        
        if (limit > req.getTotalReleaseOSBalance()) {
            return true;
        }

        return false;
    }

    public static double sumRepayments(ArrayList repaymentHolder) {
        ArrayList store = new ArrayList();
        double limit = 0;

        for (int i = 1; i < repaymentHolder.size(); i++) {
            store = (ArrayList) repaymentHolder.get(i);
            limit += Double.parseDouble(store.get(0).toString());
        }

        return limit;
    }

    public static double sumRepayments2(ArrayList<APCPRelease> releaseHolder) {
        double sumCurrentReleases = 0;
        for (APCPRelease rel : releaseHolder) {
            sumCurrentReleases += rel.getReleaseAmount();
        }

        return sumCurrentReleases;
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
