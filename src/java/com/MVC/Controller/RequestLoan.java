/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRequest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class RequestLoan extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        APCPRequestDAO requestDAO = new APCPRequestDAO();
        APCPRequest apcpRequest = new APCPRequest();

        String[] vals = request.getParameter("loan").split(",");
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(val);
        }

        String finLoan = sb.toString();

        apcpRequest.setArboID(Integer.parseInt(request.getParameter("arboID")));
        apcpRequest.setHectares(Double.parseDouble(request.getParameter("land")));
        apcpRequest.setLoanAmount(Double.parseDouble(finLoan));

        apcpRequest.setLoanReason(Integer.parseInt(request.getParameter("reason")));
        apcpRequest.setOtherLoanReason(request.getParameter("otherReason"));
        apcpRequest.setApcpType(Integer.parseInt(request.getParameter("apcpType")));

        apcpRequest.setRemarks(request.getParameter("remarks"));

        if (request.getParameter("newAccessing") != null) {
            apcpRequest.setRequestStatus(6); // NEW ACCESSING
            request.setAttribute("success", "APCP access requested!");
            request.setAttribute("errMessage", "Error in requesting access to APCP. Try again.");
        } else {
            apcpRequest.setRequestStatus(1); // REQUESTED
            request.setAttribute("success", "APCP requested!");
            request.setAttribute("errMessage", "Error in requesting APCP. Try again.");
        }
        
        String[] recipients = request.getParameterValues("recipients");

        int requestID = requestDAO.requestAPCP(apcpRequest, (Integer)session.getAttribute("userID"));
        
        for(String id : recipients){
            requestDAO.addAPCPRecipient(requestID, Integer.parseInt(id));
        }
        
        request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);

    }

}
