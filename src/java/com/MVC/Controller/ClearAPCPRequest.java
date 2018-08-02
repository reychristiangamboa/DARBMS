/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.MessageDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.APCPDocument;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.Message;
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
public class ClearAPCPRequest extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        int requestID = Integer.parseInt(request.getParameter("requestID"));
        APCPRequestDAO requestDAO = new APCPRequestDAO();
        APCPRequest req = requestDAO.getRequestByID(requestID);
        boolean verifiedDocs = true;

        for (APCPDocument doc : req.getApcpDocument()) {
            if (!doc.isIsApproved()) {
                verifiedDocs = false;
            }
        }

        if (verifiedDocs && req.checkARBOHadAPCPOrientation()) {
            if (requestDAO.clearRequest(requestID, (Integer) session.getAttribute("userID"))) {

                MessageDAO messageDAO = new MessageDAO();
                UserDAO userDAO = new UserDAO();

                Message m = new Message();
                m.setBody("[RE:APCP REQUEST CLEARED] APCP Request #" + requestID + " is cleared for endorsement.");
                m.setSentBy((Integer) session.getAttribute("userID"));
                int messageID = messageDAO.addMessage(m);

                ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(6, (Integer) session.getAttribute("provOfficeCode")); // PFO-APCP

                for (int userID : userIDs) {
                    if (messageDAO.sendMessage(messageID, userID)) {
                        System.out.println("Message sent!");
                    }
                }

                request.setAttribute("success", "APCP Request cleared!");
                request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in clearing APCP Request.");
                request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errMessage", "Please make sure documents are verified and APCP Orientation is implemented.");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        }

    }

}
