/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.IssueDAO;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.ARB;
import com.MVC.Model.Disbursement;
import com.MVC.Model.Issue;
import com.MVC.Model.Repayment;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class RecordDisbursement extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        APCPRequestDAO dao = new APCPRequestDAO();
        IssueDAO issueDAO = new IssueDAO();
        int id = 0;

        String[] arbIDs = request.getParameterValues("arbID");

        APCPRequest req = dao.getRequestByID(Integer.parseInt(request.getParameter("requestID")));
        req.setDisbursements(dao.getAllDisbursementsByRequest(req.getRequestID()));

        double limit = 0;
        double finalLimit = 0;
        double lol = 0;

        req.setDisbursements(dao.getAllDisbursementsByRequest(req.getRequestID()));
        for (Disbursement disbursement : req.getDisbursements()) {
            limit += disbursement.getDisbursedAmount();
        }

        finalLimit = req.getTotalReleasedAmountPerRequest() - limit;

        double finalVal = Double.parseDouble(request.getParameter("disbursementAmount")) * arbIDs.length;
        double OSBalance = Double.parseDouble(request.getParameter("disbursementAmount")) * req.getLoanReason().getLoanTerm().getArbInterestRate();
//        double finalVal = Double.parseDouble(request.getParameter("disbursementAmount")) * arbIDs.length;

        if (finalVal > finalLimit) {
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("errMessage", "Disbursement amount (Php " + finalVal + ") exceeds Release amount (Php " + finalLimit + "). Try again.");
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        } else {

            for (String arbID : arbIDs) {
                Disbursement d = new Disbursement();

                d.setArbID(Integer.parseInt(arbID));
                d.setRequestID(Integer.parseInt(request.getParameter("requestID")));

                d.setDisbursedAmount(Double.parseDouble(request.getParameter("disbursementAmount")));
                d.setOSBalance(OSBalance);

                java.sql.Date disbursedDate = null;

                try {
                    java.util.Date parsedDisbursedDate = sdf.parse(request.getParameter("disbursedDate"));
                    disbursedDate = new java.sql.Date(parsedDisbursedDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(RecordDisbursement.class.getName()).log(Level.SEVERE, null, ex);
                }

                d.setDateDisbursed(disbursedDate);
                d.setDisbursedBy((Integer) session.getAttribute("userID"));
                d.setTotalReleasedAmount(req.getTotalReleasedAmountPerRequest() - getTotalDisbursement2(req.getDisbursements()));
                lol = (req.getTotalReleasedAmountPerRequest() - getTotalDisbursement2(req.getDisbursements())) * .5;

                id = dao.addDisbursement(d);

            }

            if (checkSingleARBDisbursement(arbIDs, req, Double.parseDouble(request.getParameter("disbursementAmount")))) {
                
                double threshold = (req.getTotalReleasedAmountPerRequest() - getTotalDisbursement2(req.getDisbursements())) * .5;
                
                request.setAttribute("errMessage", "Disbursement amount surpassed the threshold (Php " + lol + " )! An issue has been raised.");

                Issue i = new Issue();
                i.setIssueType(3); // SINGLE DISBURSEMENT
                i.setIssuedTo(2); // PP-APCP
                i.setIssuedBy((Integer) session.getAttribute("userID"));
                i.setProvOfficeCode((Integer) session.getAttribute("provOfficeCode"));
                i.setRequestID(id); // DISBURSEMENT ID

                if (issueDAO.raiseIssue(i)) {
                    System.out.println("ISSUE RAISED!");
                }
            } else {
                request.setAttribute("success", "Disbursement successfully recorded!");
            }

            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);

        }

    }

    public boolean checkSingleARBDisbursement(String[] arbIDsStr, APCPRequest req, double disbursedAmount) {

        ARBDAO arbDAO = new ARBDAO();
        APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();

        for (String arbIDStr : arbIDsStr) {

            double total = 0;
            ARB arb = arbDAO.getARBByID(Integer.parseInt(arbIDStr));
            arb.setDisbursements(apcpRequestDAO.getAllDisbursementsByARBPerRequest(arb.getArbID(), req.getRequestID()));
            total = getTotalDisbursement(arb.getDisbursements()) + disbursedAmount;

            req.setDisbursements(apcpRequestDAO.getAllDisbursementsByRequest(req.getRequestID()));

            double threshold = req.getTotalReleasedAmountPerRequest() - getTotalDisbursement(req.getDisbursements());

            System.out.println("TOTAL: " + total);
            System.out.println("THRESHOLD " + threshold * .5);

            if (total > (threshold * .5)) { // THRESHOLD: 50%
                return true;
            }

        }

        return false;

    }

    public double getTotalDisbursement(ArrayList<Disbursement> list) {

        double total = 0;

        for (Disbursement d : list) {
            total += d.getDisbursedAmount();
        }

        if (!list.isEmpty()) {
            total -= list.get(list.size() - 1).getDisbursedAmount();
        }

        return total;
    }
    
    public double getTotalDisbursement2(ArrayList<Disbursement> list) {

        double total = 0;

        for (Disbursement d : list) {
            total += d.getDisbursedAmount();
        }

        return total;
    }

}
