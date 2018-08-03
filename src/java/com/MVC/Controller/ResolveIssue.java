/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.IssueDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.ARB;
import com.MVC.Model.Crop;
import com.MVC.Model.Issue;
import com.MVC.Model.LoanReason;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class ResolveIssue extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int issueID = Integer.parseInt(request.getParameter("issueID"));
        HttpSession session = request.getSession();

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        IssueDAO issueDAO = new IssueDAO();

        int type = Integer.parseInt(request.getParameter("type"));
        int requestID = 0;
        int planID = 0;
        int pastDueAccountID = 0;

        String message = "";

        if (type == 1) { // ARB - CROP MISMATCH

            requestID = Integer.parseInt(request.getParameter("requestID"));
            APCPRequest req = requestDAO.getRequestByID(requestID);
            req.setRecipients(requestDAO.getAllAPCPRecipientsByRequest(requestID));

//            if (checkMismatchRecipients(req.getRecipients(), req.getLoanReason().getLoanReason())) {
//                Issue i = new Issue();
//                i.setIssueType(1); // CROP MISMATCH
//                i.setIssuedTo(3); // PFO-HEAD
//                i.setIssuedBy((Integer) session.getAttribute("userID"));
//                i.setProvOfficeCode((Integer) session.getAttribute("provOfficeCode"));
//                i.setRequestID(requestID);
//
//                if (issueDAO.raiseIssue(i)) {
//                    System.out.println("ISSUE RAISED!");
//                    request.getRequestDispatcher("view-issues.jsp").forward(request, response);
//                }
//            } else {
                message = "ARB - CROP MISMATCH";
                requestID = Integer.parseInt(request.getParameter("requestID"));
                requestDAO.updateRequestStatus(requestID, 1); // REQUESTED
//                request.setAttribute("errMessage", "CROP MISMATCH! Issue has been raised!");  
//            }

        } else if (type == 2) { // CAPDEV - MANDATORY TRAINING ABSENCE
            message = "CAPDEV - MANDATORY TRAINING ABSENCE";
            int activityID = Integer.parseInt(request.getParameter("activityID"));
            request.setAttribute("success", "Issue: " + message + " for Activity #" + activityID + " has been resolved!");
        } else if (type == 3) { // APCP - SINGLE ARB DISBURSEMENT
            message = "APCP - SINGLE ARB DISBURSEMENT";
            int disbursementID = Integer.parseInt(request.getParameter("disbursementID"));
            request.setAttribute("success", "Issue: " + message + " for Disbursement #" + disbursementID + " has been resolved!");
        } else if (type == 4) { // APCP - SINGLE ARB DISBURSEMENT
            message = "APCP - PAST DUE ACCOUNT";
            pastDueAccountID = Integer.parseInt(request.getParameter("pastDueAccountID"));
            request.setAttribute("success", "Issue: " + message + " for Past Due Account #" + pastDueAccountID + " has been resolved!");
        }

        Issue i = new Issue();
        i.setFindings(request.getParameter("findings"));
        i.setResolution(request.getParameter("resolution"));
        i.setId(issueID);

        if (issueDAO.resolveIssue(i)) {
            System.out.println("ISSUE RESOLVED!");
        }

        request.getRequestDispatcher("view-issues.jsp").forward(request, response);

    }

    public boolean checkMismatchRecipients(ArrayList<ARB> recipients, int loanReason) {

        ARBDAO arbDAO = new ARBDAO();
        APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
        boolean found = false;

        LoanReason r = new LoanReason();
        r = apcpRequestDAO.getLoanReasonById(loanReason);

        for (ARB arb : recipients) {
            found = false;
            arb.setCurrentCrops(arbDAO.getAllARBCurrentCrops(arb.getArbID()));

            for (Crop c : arb.getCurrentCrops()) {
                if (r.getLoanReasonDesc().contains(c.getCropTypeDesc())) { // RETURNS TRUE IF LOAN REASON DOES NOT CONTAIN ARBs' CURRENT CROP
                    found = true;
                }
            }

            if (!found) {
                return true;
            }

        }

        return false;

    }

}
