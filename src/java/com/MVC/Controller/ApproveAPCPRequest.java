/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.MessageDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.Message;
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
public class ApproveAPCPRequest extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        java.sql.Date date = null;

        try {
            java.util.Date parsedDate = sdf.parse(request.getParameter("approveDate"));
            date = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ApproveAPCPRequest.class.getName()).log(Level.SEVERE, null, ex);
        }

        int requestID = Integer.parseInt(request.getParameter("requestID"));

        APCPRequestDAO requestDAO = new APCPRequestDAO();

        if (requestDAO.approveRequest(requestID, (Integer) session.getAttribute("userID"), date)) {

            MessageDAO messageDAO = new MessageDAO();
            UserDAO userDAO = new UserDAO();

            Message m = new Message();
            m.setBody("[RE:PRE-RELEASE ORIENTATION] APCP Request #" + requestID + " has been APPROVED! Please schedule a Pre-Release Orientation immediately.");
            m.setSentBy((Integer) session.getAttribute("userID"));
            int messageID = messageDAO.addMessage(m);

            ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(7, (Integer) session.getAttribute("provOfficeCode")); // PFO-CAPDEV

            for (int userID : userIDs) {
                if (messageDAO.sendMessage(messageID, userID)) {
                    System.out.println("Message sent!");
                }
            }

            m.setBody("[RE:APCP REQUEST APPROVED] APCP Request #" + requestID + " has now been approved. Waiting for implementation of Pre-Release Orientation.");
            m.setSentBy((Integer) session.getAttribute("userID"));
            int messageID2 = messageDAO.addMessage(m);

            ArrayList<Integer> userIDs2 = userDAO.retrieveProvincialUserIDsByUserType(6, (Integer) session.getAttribute("provOfficeCode")); // PFO-APCP

            for (int userID : userIDs2) {
                if (messageDAO.sendMessage(messageID2, userID)) {
                    System.out.println("Message sent!");
                }
            }

            request.setAttribute("success", "APCP Request approved!");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in approving APCP Request.");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        }
    }

}
