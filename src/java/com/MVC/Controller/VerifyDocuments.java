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
 * @author ijJPN
 */
public class VerifyDocuments extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String [] documentIDs = request.getParameterValues("documentID");
        APCPRequestDAO dao = new APCPRequestDAO();
        
        
        for(String idStr : documentIDs){
            if(dao.approveDocument(Integer.parseInt(idStr))){
            }
        }
        
        dao.setDateCompleted(Integer.parseInt(request.getParameter("requestID")));
        
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        
        request.setAttribute("requestID", requestID);
        request.getRequestDispatcher("view-apcp-application.jsp").forward(request, response);
        
    }

}
