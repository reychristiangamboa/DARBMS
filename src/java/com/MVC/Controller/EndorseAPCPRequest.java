/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
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
public class EndorseAPCPRequest extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        int ltn = Integer.parseInt(request.getParameter("ltn"));
        APCPRequestDAO requestDAO = new APCPRequestDAO();
        
        if(requestDAO.endorseRequest(requestID, (Integer)session.getAttribute("userID"), ltn)){
            request.setAttribute("success", "APCP Request endorsed!");
            request.getRequestDispatcher("provincial-field-officer-view-loan-status.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in endorsing APCP Request.");
            request.getRequestDispatcher("provincial-field-officer-view-loan-status.jsp").forward(request, response);
        }
    }
}
