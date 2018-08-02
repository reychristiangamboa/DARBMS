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

/**
 *
 * @author Rey Christian
 */
public class RemoveRecipients extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String[] arbIDsStr = request.getParameterValues("arbID");
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        
        APCPRequestDAO dao = new APCPRequestDAO();
        
        for(String arbIDStr : arbIDsStr){
            dao.removeAPCPRecipient(requestID, Integer.parseInt(arbIDStr));
        }
        
        request.setAttribute("success", "RECIPIENTS for Request ID " + requestID + " has been successfully removed!");
        request.getRequestDispatcher("view-issues.jsp").forward(request, response);
        
    }

}
