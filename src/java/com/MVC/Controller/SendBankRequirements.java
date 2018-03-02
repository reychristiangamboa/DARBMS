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

/**
 *
 * @author Rey Christian
 */
public class SendBankRequirements extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        APCPRequestDAO dao = new APCPRequestDAO();

        int requestID = Integer.parseInt(request.getParameter("requestID"));

        java.sql.Date bankRequirementsDate = null;

        try {
            java.util.Date parsedBankRequirementsDate = sdf.parse(request.getParameter("bankRequirementsDate"));
            bankRequirementsDate = new java.sql.Date(parsedBankRequirementsDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(SendBankRequirements.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(dao.sendBankRequirements(bankRequirementsDate, requestID)){
            request.setAttribute("requestID", requestID);
            request.getRequestDispatcher("provincial-field-officer-check-request-document.jsp").forward(request, response);
        }else{
            request.setAttribute("requestID", requestID);
            request.getRequestDispatcher("provincial-field-officer-check-request-document.jsp").forward(request, response);
        }
    }

}
