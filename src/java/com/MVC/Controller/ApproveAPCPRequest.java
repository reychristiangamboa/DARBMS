/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
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
        
        if(requestDAO.approveRequest(requestID, (Integer)session.getAttribute("userID"), date)){
            request.setAttribute("success", "APCP Request approved!");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in approving APCP Request.");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        }
    }

}
