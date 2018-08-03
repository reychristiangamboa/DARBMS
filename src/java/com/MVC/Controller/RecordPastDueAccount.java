/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.IssueDAO;
import com.MVC.Model.Issue;
import com.MVC.Model.PastDueAccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class RecordPastDueAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HttpSession session = request.getSession();

        APCPRequestDAO dao = new APCPRequestDAO();
        IssueDAO issueDAO = new IssueDAO();
        PastDueAccount pda = new PastDueAccount();

        pda.setRequestID(Integer.parseInt(request.getParameter("requestID")));
        
        
        String[] vals = request.getParameter("pastDueAmount").split(",");
        StringBuilder sb = new StringBuilder();
        for(String val : vals){
            sb.append(val);
        }
        
        String finAmount = sb.toString();
        pda.setPastDueAmount(Double.parseDouble(finAmount));
        
        pda.setReasonPastDue(Integer.parseInt(request.getParameter("reasonPastDue")));
        pda.setOtherReason(request.getParameter("otherReason"));
        pda.setRecordedBy((Integer) session.getAttribute("userID"));

        java.sql.Date recordedDate = null;

        try {
            java.util.Date parsedRecordedDate = sdf.parse(request.getParameter("recordedDate"));
            recordedDate = new java.sql.Date(parsedRecordedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(RecordPastDueAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        pda.setDateRecorded(recordedDate);

        int pastDueAccountID = dao.addPastDueAccount(pda);
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            
            
            request.setAttribute("errMessage", "APCP pended! An issue has been raised to the PFO-CAPDEV.");
                
                Issue i = new Issue();
                i.setIssueType(4); // PAST DUE SCHEDULE CAPDEV
                i.setIssuedTo(7); // PFO-CAPDEV
                i.setIssuedBy((Integer)session.getAttribute("userID"));
                i.setProvOfficeCode((Integer)session.getAttribute("provOfficeCode"));
                i.setRequestID(Integer.parseInt(request.getParameter("requestID")));
                i.setPastDueAccountID(pastDueAccountID);
                
                if(issueDAO.raiseIssue(i)){
                    System.out.println("ISSUE RAISED!");
                }
            
            
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        

    }
}
