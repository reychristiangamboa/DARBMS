/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
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
        PastDueAccount pda = new PastDueAccount();

        pda.setRequestID(Integer.parseInt(request.getParameter("requestID")));
        pda.setPastDueAmount(Double.parseDouble(request.getParameter("pastDueAmount")));
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

        if (dao.addPastDueAccount(pda)) {
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("success", "Past Due Account successfully recorded!");
            request.getRequestDispatcher("point-person-monitor-release.jsp").forward(request, response);
        } else {
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("success", "Error in recording Past Due Account.");
            request.getRequestDispatcher("point-person-monitor-release.jsp").forward(request, response);
        }

    }
}
