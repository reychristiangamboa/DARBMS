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
import java.sql.Date;
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
public class ResubmitDocuments extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long l = System.currentTimeMillis();
        Date d = new Date(l);

        String[] documentIDsStr = request.getParameterValues("documentID"); // document referenceID
        String[] dateSubmittedStr = request.getParameterValues("dateSubmitted");
        int reqID = Integer.parseInt(request.getParameter("requestID"));

        APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
        MessageDAO messageDAO = new MessageDAO();
        UserDAO userDAO = new UserDAO();

        APCPRequest r = apcpRequestDAO.getRequestByID(reqID);

        r.setRequestStatus(1); // REQUESTED
//        for (String str : dateSubmittedStr) {
//            if (str.isEmpty()) {
//                r.setRequestStatus(8); // INCOMPLETE
//            }
//        }

        if (r.getRequestStatus() == 1) {
            r.setDateCompleted(d);

            Message m = new Message();
            m.setBody("[RE:APCP ORIENTATION] A new APCP Request has been REQUESTED! Please schedule an APCP Orientation immediately.");
            m.setSentBy((Integer) session.getAttribute("userID"));
            int messageID = messageDAO.addMessage(m);

            ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(7, (Integer) session.getAttribute("provOfficeCode")); // PFO-CAPDEV

            for (int userID : userIDs) {
                if (messageDAO.sendMessage(messageID, userID)) {
                    System.out.println("Message sent!");
                }
            }

        }

        for (int i = 0; i < documentIDsStr.length; i++) {
            
            System.out.println("DOCUMENT: " + documentIDsStr[i]);
            APCPDocument doc = new APCPDocument();
            doc.setDocument(Integer.parseInt(documentIDsStr[i])); // set documentID

            java.sql.Date dateSubmitted = null;

            try {
                if (!dateSubmittedStr[i].isEmpty()) { // dateSubmitted has value
                    java.util.Date parsedDateSubmitted = sdf.parse(dateSubmittedStr[i]);
                    dateSubmitted = new java.sql.Date(parsedDateSubmitted.getTime());
                    doc.setDateSubmitted(dateSubmitted); // set dateSubmitted
                }
            } catch (ParseException ex) {
                Logger.getLogger(RequestConduit.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (apcpRequestDAO.resubmitDocument(doc, reqID) && apcpRequestDAO.updateRequestStatus(reqID, 1)) {
                System.out.println("Document resubmitted!");
            }
        }
        
        request.setAttribute("success", "Documents successfully submitted!");
        request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);

    }

}
