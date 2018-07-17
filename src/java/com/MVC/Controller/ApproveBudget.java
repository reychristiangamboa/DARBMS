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
public class ApproveBudget extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        AddressDAO addressDAO = new AddressDAO();
        ProvincialBudget p = new ProvincialBudget();

        int id = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");

        java.sql.Date startDate = null;

        try {
            java.util.Date parsedStartDate = sdf.parse(request.getParameter("startDate"));
            startDate = new java.sql.Date(parsedStartDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ApproveBudget.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.sql.Date endDate = null;

        try {
            java.util.Date parsedEndDate = sdf.parse(request.getParameter("endDate"));
            endDate = new java.sql.Date(parsedEndDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ApproveBudget.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        p.setId(id);
        p.setStartDate(startDate);
        p.setEndDate(endDate);
        p.setApprovedBy((Integer)session.getAttribute("userID"));
        
        if(type.equalsIgnoreCase("APCP")){
            addressDAO.approveAPCPBudget(p);
        }else{
            addressDAO.approveCAPDEVBudget(p);
        }
        
        request.setAttribute("success", "Budget approved successfully!");
        request.getRequestDispatcher("CO-requested-budgets.jsp").forward(request, response);

    }

}
