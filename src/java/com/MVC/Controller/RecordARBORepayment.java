/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.MVC.DAO.*;
import com.MVC.Model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rey Christian
 */
public class RecordARBORepayment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] arbIDs = request.getParameterValues("arbIDs");
        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRequest req = dao.getRequestByID(Integer.parseInt(request.getParameter("requestID")));

        double limit = 0;

        req.setArboRepayments(dao.getAllARBORepaymentsByRequest(req.getRequestID()));
        for (Repayment rep : req.getArboRepayments()) {
            limit += rep.getAmount();
        }

        String[] vals = request.getParameter("arboRepaymentAmount").split(",");
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(val);
        }

        String finAmount = sb.toString();
        double finalVal = Double.parseDouble(finAmount) + limit;

        if (finalVal > req.getTotalReleaseOSBalance()) {
            request.setAttribute("errMessage", "REPAYMENT amount (PHP " + finalVal + ") exceeds O/S Balance amount (PHP " + req.getTotalReleaseOSBalance() + "). Try again.");
            request.setAttribute("requestID", req.getRequestID());
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        } else {

            Repayment r = new Repayment();

            
            r.setRequestID(Integer.parseInt(request.getParameter("requestID")));

            r.setAmount(Double.parseDouble(finAmount));

            java.sql.Date repaymentDate = null;

            try {
                java.util.Date parsedRepaymentDate = sdf.parse(request.getParameter("arboRepaymentDate"));
                repaymentDate = new java.sql.Date(parsedRepaymentDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(RecordARBORepayment.class.getName()).log(Level.SEVERE, null, ex);
            }

            r.setDateRepayment(repaymentDate);
            r.setRecordedBy((Integer) session.getAttribute("userID"));

            dao.addARBORepayment(r);

            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("success", "ARBO Repayment successfully recorded!");
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        }
    }

}
