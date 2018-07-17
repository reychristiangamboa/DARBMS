/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
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
public class DisapproveBudget extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        AddressDAO addressDAO = new AddressDAO();
        String type = request.getParameter("type");
        int id = Integer.parseInt(request.getParameter("id"));
        
        if(type.equalsIgnoreCase("APCP")){
            addressDAO.disapproveAPCPBudget(id);
        }else{
            addressDAO.disapproveCAPDEVBudget(id);
        }
        
        request.setAttribute("success", "Budget approved successfully!");
        request.getRequestDispatcher("CO-requested-budgets.jsp").forward(request, response);
        
    }

    

}
