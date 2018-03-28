/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.Disbursement;
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
public class RecordDisbursement extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        APCPRequestDAO dao = new APCPRequestDAO();

        String[] arbIDs = request.getParameterValues("arbID");

        APCPRelease release = dao.getAPCPReleaseByID(Integer.parseInt(request.getParameter("releaseID")));

        double limit = 0;
        double finalLimit = 0;

        for (Disbursement disbursement : release.getDisbursements()) {
            limit += disbursement.getDisbursedAmount();
        }

        finalLimit = release.getReleaseAmount() - limit;

        String[] vals = request.getParameter("disbursementAmount").split(",");
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(val);
        }
        
        String[] vals2 = request.getParameter("OSBalance").split(",");
        StringBuilder sb2 = new StringBuilder();
        for (String val : vals2) {
            sb2.append(val);
        }

        String finAmount = sb.toString();
        String OSBalance = sb2.toString();

        double finalVal = Double.parseDouble(finAmount) * arbIDs.length;

        if (finalVal > finalLimit) {
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("releaseID", Integer.parseInt(request.getParameter("releaseID")));
            request.setAttribute("errMessage", "Disbursement amount (Php " + finalVal + ") exceeds Release amount (Php " + finalLimit + "). Try again.");
            request.getRequestDispatcher("monitor-disbursement.jsp").forward(request, response);
        } else {
            for (String arbID : arbIDs) {
                Disbursement d = new Disbursement();

                d.setArbID(Integer.parseInt(arbID));
                d.setReleaseID(Integer.parseInt(request.getParameter("releaseID")));

                d.setDisbursedAmount(Double.parseDouble(finAmount));
                d.setOSBalance(Double.parseDouble(OSBalance));

                java.sql.Date disbursedDate = null;

                try {
                    java.util.Date parsedDisbursedDate = sdf.parse(request.getParameter("disbursedDate"));
                    disbursedDate = new java.sql.Date(parsedDisbursedDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(RecordDisbursement.class.getName()).log(Level.SEVERE, null, ex);
                }

                d.setDateDisbursed(disbursedDate);
                d.setDisbursedBy((Integer) session.getAttribute("userID"));

                dao.addDisbursement(d);

            }

            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.setAttribute("releaseID", Integer.parseInt(request.getParameter("releaseID")));
            request.setAttribute("success", "Disbursement successfully recorded!");
            request.getRequestDispatcher("monitor-disbursement.jsp").forward(request, response);

        }

    }

}
