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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class RecordPastDueAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        APCPRequestDAO dao = new APCPRequestDAO();
        PastDueAccount pda = new PastDueAccount();
        
        pda.setRequestID(Integer.parseInt(request.getParameter("requestID")));
        pda.setPastDueAmount(Double.parseDouble(request.getParameter("pastDueAmount")));
        pda.setReasonPastDue(Integer.parseInt(request.getParameter("reasonPastDue")));
        pda.setRecordedBy((Integer)session.getAttribute("userID"));
        
        if(dao.addPastDueAccount(pda)){
            request.setAttribute("success", "Past Due Account successfully recorded!");
            request.getRequestDispatcher("").forward(request, response);
        }else{
            request.setAttribute("success", "Error in recording Past Due Account.");
            request.getRequestDispatcher("").forward(request, response);
        }
        
    }
}
