/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.PastDueAccount;
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
public class SettlePastDueAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        APCPRequestDAO dao = new APCPRequestDAO();
        PastDueAccount pda = new PastDueAccount();
        
        java.sql.Date dateSettled = null;

        try {
            java.util.Date parsedDateSettled = sdf.parse(request.getParameter("dateSettled"));
            dateSettled = new java.sql.Date(parsedDateSettled.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(SendBankRequirements.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pda.setPastDueAccountID(Integer.parseInt(request.getParameter("pastDueAccountID")));
        pda.setDateSettled(dateSettled);
        
        if(dao.settlePastDueAccount(pda)){
            request.setAttribute("success", "Past Due Account successfully settled!");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("point-person-monitor-release.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in settling Past Due Account.");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("point-person-monitor-release.jsp").forward(request, response);
        }
        
        
    }

    

}
