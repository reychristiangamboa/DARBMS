/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.MessageDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.Issue;
import com.MVC.Model.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.servlet.ServletException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ijJPN
 */
public class SendCAPDEVAssessment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int planID = Integer.parseInt(request.getParameter("planID"));

        HttpSession session = request.getSession();
        MessageDAO messageDAO = new MessageDAO();
        UserDAO userDAO = new UserDAO();
        CAPDEVDAO dao = new CAPDEVDAO();
        APCPRequestDAO dao2 = new APCPRequestDAO();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CAPDEVPlan plan = dao.getCAPDEVPlan(planID);

        java.sql.Date implementedDate = null;

        try {
            java.util.Date parsedImplementedDate = sdf.parse(request.getParameter("implementationDate"));
            implementedDate = new java.sql.Date(parsedImplementedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(SendCAPDEVAssessment.class.getName()).log(Level.SEVERE, null, ex);
        }

        dao.assessCAPDEV(request.getParameter("observation"), request.getParameter("recommendation"), planID);

        if (plan.hasPreReleaseOrientation()) { // IF PLAN HAS PRE-RELEASE, UPDATE REQUEST STATUS TO FOR-RELEASE
            if (dao.updatePlanStatus(planID, 5, implementedDate) && dao2.updateRequestStatus(plan.getRequestID(), 6)) {

                Message m = new Message();
                m.setBody("[RE:PRE-RELEASE IMPLEMENTED] Pre-release Orientation IMPLEMENTED! APCP Request #" + plan.getRequestID() + " is now FOR RELEASE.");
                m.setSentBy((Integer) session.getAttribute("userID"));
                int messageID = messageDAO.addMessage(m);

                ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(6, (Integer) session.getAttribute("provOfficeCode")); // PFO-APCP
                ArrayList<Integer> userIDs2 = userDAO.retrieveProvincialUserIDsByUserType(2, (Integer) session.getAttribute("provOfficeCode")); // PP-APCP

                for (int userID : userIDs) {
                    if (messageDAO.sendMessage(messageID, userID)) {
                        System.out.println("Message sent!");
                    }
                }

                for (int userID : userIDs2) {
                    if (messageDAO.sendMessage(messageID, userID)) {
                        System.out.println("Message sent!");
                    }
                }

                request.setAttribute("success", "CAPDEV Assessment sent!");
                request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in sending CAPDEV assessment.");
                request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
            }
        } else {

            if (plan.hasAPCPOrientation()) {
                Message m = new Message();
                m.setBody("[RE:APCP IMPLEMENTED] APCP Orientation IMPLEMENTED! APCP Request is now ready to be CLEARED.");
                m.setSentBy((Integer) session.getAttribute("userID"));
                int messageID = messageDAO.addMessage(m);

                ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(6, (Integer) session.getAttribute("provOfficeCode")); // PFO-APCP

                for (int userID : userIDs) {
                    if (messageDAO.sendMessage(messageID, userID)) {
                        System.out.println("Message sent!");
                    }
                }
            }
            if (dao.updatePlanStatus(planID, 5, implementedDate)) {
                request.setAttribute("success", "CAPDEV Assessment sent!");
                if (plan.getPastDueAccountID() > 0) {
                    Message m = new Message();
                    m.setBody("[RE:PAST DUE CAPDEV PLAN IMPLEMENTED] PAST DUE CAPDEV PLAN IMPLEMENTED! Past Due #" + plan.getPastDueAccountID()+ " is now FOR RELEASE. Please resolve this.");
                    m.setSentBy((Integer) session.getAttribute("userID"));
                    int messageID = messageDAO.addMessage(m);

                    ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(7, (Integer) session.getAttribute("provOfficeCode")); // PFO-CAPDEV
                    
                    for (int userID : userIDs) {
                        if (messageDAO.sendMessage(messageID, userID)) {
                            System.out.println("Message sent!");
                        }
                    }

                }
                request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in sending CAPDEV assessment.");
                request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
            }
        }

    }

}
