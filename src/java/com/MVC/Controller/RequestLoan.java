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

        apcpRequest.setArboID(Integer.parseInt(request.getParameter("arboID")));
        apcpRequest.setHectares(Double.parseDouble(request.getParameter("land")));
        apcpRequest.setLoanAmount(Double.parseDouble(request.getParameter("loan")));
        apcpRequest.setLoanReason(request.getParameter("reason"));
        apcpRequest.setRemarks(request.getParameter("remarks"));

        if (requestDAO.requestAPCP(apcpRequest, (Integer)session.getAttribute("userID"))) {
            request.setAttribute("arboID", apcpRequest.getArboID());
            request.setAttribute("success", "APCP requested!");
            request.getRequestDispatcher("provincial-field-officer-view-loan-status.jsp").forward(request, response);
        } else {
            request.setAttribute("arboID", apcpRequest.getArboID());
            request.setAttribute("errMessage", "Error in requesting APCP. Try again.");
            request.getRequestDispatcher("provincial-field-officer-view-loan-status.jsp").forward(request, response);
        }

    }

}