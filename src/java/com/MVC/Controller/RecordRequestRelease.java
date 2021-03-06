/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRelease;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.PastDueAccount;
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
public class RecordRequestRelease extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRelease r = new APCPRelease();
        APCPRequest req = dao.getRequestByID(Integer.parseInt(request.getParameter("requestID")));

        r.setRequestID(Integer.parseInt(request.getParameter("requestID")));
        
//        String[] vals = request.getParameter("releaseAmount").split(",");
//        StringBuilder sb = new StringBuilder();
//        for(String val : vals){
//            sb.append(val);
//        }
//        
//        String finAmount = sb.toString();
        
        r.setReleaseAmount(Double.parseDouble(request.getParameter("releaseAmount")));

//        r.setReleaseAmount(Double.parseDouble(request.getParameter("releaseAmount")));

        java.sql.Date releaseDate = null;

        try {
            java.util.Date parsedReleaseDate = sdf.parse(request.getParameter("releaseDate"));
            releaseDate = new java.sql.Date(parsedReleaseDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        double OSBalance = r.getReleaseAmount() * req.getLoanReason().getLoanTerm().getArboInterestRate();

        r.setReleaseDate(releaseDate);
        r.setOSBalance(OSBalance);
        r.setReleasedBy((Integer) session.getAttribute("userID"));

        if (!dao.requestHasRelease(req)) {
            dao.updateRequestStatus(req.getRequestID(), 5);
        }

        double limit = 0;
        double finalLimit = 0;

        req.setReleases(dao.getAllAPCPReleasesByRequest(req.getRequestID()));
        for (APCPRelease release : req.getReleases()) {
            limit += release.getReleaseAmount();
        }

        finalLimit = req.getLoanAmount() - limit;

        if (r.getReleaseAmount() > finalLimit) {
            request.setAttribute("errMessage", "RELEASE amount (PHP "+r.getReleaseAmount()+") exceeds REQUEST amount (PHP "+finalLimit+"). Try again.");
            request.setAttribute("requestID", r.getRequestID());
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        } else {
            if (dao.addRequestRelease(r)) {
                request.setAttribute("success", "Request Release successfully recorded!");
                request.setAttribute("requestID", r.getRequestID());
                request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in recording Request Release.");
                request.setAttribute("requestID", r.getRequestID());
                request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
            }
        }

    }
}
