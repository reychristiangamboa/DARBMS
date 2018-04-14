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
        for(String val : vals){
            sb.append(val);
        }
        
        String finLoan = sb.toString();
        System.out.println(request.getParameter("loan"));
        System.out.println(finLoan);

        apcpRequest.setArboID(Integer.parseInt(request.getParameter("arboID")));
        apcpRequest.setHectares(Double.parseDouble(request.getParameter("land")));
        apcpRequest.setLoanAmount(Double.parseDouble(finLoan));
        
        apcpRequest.setLoanReason(Integer.parseInt(request.getParameter("reason")));
        apcpRequest.setOtherLoanReason(request.getParameter("otherReason"));
        apcpRequest.setApcpType(Integer.parseInt(request.getParameter("apcpType")));
        
        apcpRequest.setRemarks(request.getParameter("remarks"));
        apcpRequest.setRequestStatus(1);

        if (requestDAO.requestAPCP(apcpRequest, (Integer)session.getAttribute("userID"))) {
            request.setAttribute("arboID", apcpRequest.getArboID());
            request.setAttribute("success", "APCP requested!");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        } else {
            request.setAttribute("arboID", apcpRequest.getArboID());
            request.setAttribute("errMessage", "Error in requesting APCP. Try again.");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        }

    }

}
