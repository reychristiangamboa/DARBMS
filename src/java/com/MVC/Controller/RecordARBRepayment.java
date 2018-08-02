/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.Repayment;
import java.io.IOException;
import java.io.PrintWriter;
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
public class RecordARBRepayment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRequest req = dao.getRequestByID(Integer.parseInt(request.getParameter("requestID")));

        double limit = 0;
        double finalLimit = 0;

        for (Repayment rep : req.getArbRepayments()) {
            limit += rep.getAmount();
        }

        String[] vals = request.getParameter("arbRepaymentAmount").split(",");
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(val);
        }

        String finAmount = sb.toString();

        double arbRepaymentAmount = Double.parseDouble(finAmount);
        double arbRepaymentOSBalance = req.getTotalARBOSBalance(Integer.parseInt(request.getParameter("arbID")));

        finalLimit = req.getLoanAmount() - limit;

        if (arbRepaymentAmount > arbRepaymentOSBalance) {
            request.setAttribute("errMessage", "REPAYMENT amount (PHP " + arbRepaymentAmount + ") exceeds O/S Balance (PHP " + arbRepaymentOSBalance + "). Try again.");
            request.setAttribute("requestID", req.getRequestID());
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        } else {

            Repayment r = new Repayment();

            r.setArbID(Integer.parseInt(request.getParameter("arbID")));
            r.setRequestID(Integer.parseInt(request.getParameter("requestID")));

            r.setAmount(Double.parseDouble(finAmount));

            java.sql.Date repaymentDate = null;

            try {
                java.util.Date parsedRepaymentDate = sdf.parse(request.getParameter("arbRepaymentDate"));
                repaymentDate = new java.sql.Date(parsedRepaymentDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(RecordARBRepayment.class.getName()).log(Level.SEVERE, null, ex);
            }

            r.setDateRepayment(repaymentDate);
            r.setRecordedBy((Integer) session.getAttribute("userID"));

            dao.addARBRepayment(r);

            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("success", "ARB Repayment successfully recorded!");
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        }
    }

}
