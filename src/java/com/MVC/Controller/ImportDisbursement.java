/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.Disbursement;
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
public class ImportDisbursement extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        APCPRequestDAO dao = new APCPRequestDAO();
        ARBDAO arbDAO = new ARBDAO();
        ArrayList disbursementHolder = readExcelFile(request.getParameter("file"));
        ArrayList store = new ArrayList();
        

        APCPRelease rel = dao.getAPCPReleaseByID(Integer.parseInt(request.getParameter("releaseID")));
        double amountLimit = rel.getReleaseAmount()-sumDibursements2(rel.getDisbursements());

        if (disbursementsExceedLimit(rel, disbursementHolder)) {
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("releaseID", Integer.parseInt(request.getParameter("releaseID")));
            request.setAttribute("errMessage", "DISBURSEMENT/S amount (Php " + sumDibursements(disbursementHolder) + ") exceeds RELEASE amount (Php " + amountLimit + "). Try again.");
            request.getRequestDispatcher("monitor-disbursement.jsp").forward(request, response);
        } else {
            for (int i = 1; i < disbursementHolder.size(); i++) {
                store = (ArrayList) disbursementHolder.get(i);
                Disbursement d = new Disbursement();

                String lN = store.get(0).toString();
                String fN = store.get(1).toString();
                String mN = store.get(2).toString();
                int arbID = arbDAO.getARBID(fN, mN, lN);
                
                

                d.setArbID(arbID);
                d.setReleaseID(Integer.parseInt(request.getParameter("releaseID")));
                d.setDisbursedAmount(Double.parseDouble(store.get(3).toString()));
                d.setOSBalance(Double.parseDouble(store.get(4).toString()));

                java.sql.Date disbursedDate = null;

                String excelDate = store.get(5).toString(); // Parsing of Excel Date to Java Date
                String[] dateArr = excelDate.split("-");

                int val = getValOfMonth(dateArr[1]);
                String finalDate = dateArr[2] + "-" + val + "-" + dateArr[0];

                try {
                    java.util.Date parsedDisbursedDate = sdf.parse(finalDate);
                    disbursedDate = new java.sql.Date(parsedDisbursedDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(ImportDisbursement.class.getName()).log(Level.SEVERE, null, ex);
                }

                d.setDateDisbursed(disbursedDate);
                d.setDisbursedBy((Integer) session.getAttribute("userID"));
               
                System.out.println(d.getArbID() + " " + d.getReleaseID() + " " + d.getDisbursedAmount() + " " + d.getOSBalance());

                dao.addDisbursement(d);
            }

            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("releaseID", Integer.parseInt(request.getParameter("releaseID")));
            request.setAttribute("success", "Disbursements successfully imported!");
            request.getRequestDispatcher("monitor-disbursement.jsp").forward(request, response);
        }

    }

    public static boolean disbursementsExceedLimit(APCPRelease rel, ArrayList disbursementHolder) {
        ArrayList store = new ArrayList();
        double limit = 0;
        double sumCurrentDisbursement = 0;
        double finalLimit = 0;

        for (int i = 1; i < disbursementHolder.size(); i++) {
            store = (ArrayList) disbursementHolder.get(i);
            limit += Double.parseDouble(store.get(3).toString());
        }
        
        for(Disbursement d : rel.getDisbursements()){
            sumCurrentDisbursement += d.getDisbursedAmount();
        }
        
        finalLimit = rel.getReleaseAmount() - sumCurrentDisbursement;

        if (limit > finalLimit) {
            return true;
        }

        return false;
    }

    public static double sumDibursements(ArrayList disbursementHolder) {
        ArrayList store = new ArrayList();
        double limit = 0;

        for (int i = 1; i < disbursementHolder.size(); i++) {
            store = (ArrayList) disbursementHolder.get(i);
            limit += Double.parseDouble(store.get(3).toString());
        }

        return limit;
    }
    
    public static double sumDibursements2(ArrayList<Disbursement> disbursements) {
        
        double sumCurrentDisbursement = 0;
        for(Disbursement d : disbursements){
            sumCurrentDisbursement += d.getDisbursedAmount();
        }
        return sumCurrentDisbursement;
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
