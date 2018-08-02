/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.IssueDAO;
import com.MVC.Model.Issue;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class ResolveIssue extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int issueID = Integer.parseInt(request.getParameter("issueID"));
        
        APCPRequestDAO requestDAO = new APCPRequestDAO();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        IssueDAO issueDAO = new IssueDAO();
        
        int type = Integer.parseInt(request.getParameter("type"));
        int requestID = 0;
        int planID = 0;
        int pastDueAccountID = 0;
        
        String message = "";
        
        if(type == 1){ // ARB - CROP MISMATCH
            message = "ARB - CROP MISMATCH";
            requestID = Integer.parseInt(request.getParameter("requestID"));
            requestDAO.updateRequestStatus(requestID, 1); // REQUESTED
            request.setAttribute("success", "Issue: " + message + " for Request #" + requestID +" has been resolved!");
        }else if(type == 2){ // CAPDEV - MANDATORY TRAINING ABSENCE
            message = "CAPDEV - MANDATORY TRAINING ABSENCE";
            int activityID = Integer.parseInt(request.getParameter("activityID"));
            request.setAttribute("success", "Issue: " + message + " for Activity #" + activityID +" has been resolved!");
        }else if(type == 3){ // APCP - SINGLE ARB DISBURSEMENT
            message = "APCP - SINGLE ARB DISBURSEMENT";
            int disbursementID = Integer.parseInt(request.getParameter("disbursementID"));
            request.setAttribute("success", "Issue: " + message + " for Disbursement #" + disbursementID +" has been resolved!");
        }else if(type == 4){ // APCP - SINGLE ARB DISBURSEMENT
            message = "APCP - PAST DUE ACCOUNT";
            pastDueAccountID = Integer.parseInt(request.getParameter("pastDueAccountID"));
            request.setAttribute("success", "Issue: " + message + " for Past Due Account #" + pastDueAccountID +" has been resolved!");
        }
        
        Issue i = new Issue();
        i.setFindings(request.getParameter("findings"));
        i.setResolution(request.getParameter("resolution"));
        i.setId(issueID);
        
        if(issueDAO.resolveIssue(i)){
            System.out.println("ISSUE RESOLVED!");
        }
        
        request.getRequestDispatcher("view-issues.jsp").forward(request, response);
        
    }

    

}
