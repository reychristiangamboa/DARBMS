/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
import com.MVC.Model.ProvincialBudget;
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
public class RequestBudget extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        AddressDAO addressDAO = new AddressDAO();
        ProvincialBudget provincialBudget = new ProvincialBudget();
        
        int budgetType = Integer.parseInt(request.getParameter("budgetType"));
        provincialBudget.setBudget(Double.parseDouble(request.getParameter("budgetRequested")));
        provincialBudget.setReason(request.getParameter("reason"));
        provincialBudget.setRequestedBy((Integer)session.getAttribute("userID"));
        provincialBudget.setProvOfficeCode((Integer)session.getAttribute("provOfficeCode"));
        provincialBudget.setRegCode((Integer)session.getAttribute("regOfficeCode"));
        
        if(budgetType == 1){
            addressDAO.requestAPCPBudget(provincialBudget);
        }else{
            addressDAO.requestCAPDEVBudget(provincialBudget);
        }
        
        request.setAttribute("success", "Budget requested successfully!");
        request.getRequestDispatcher("PFO-HEAD-request-budget.jsp").forward(request, response);
        
    }

    

}
