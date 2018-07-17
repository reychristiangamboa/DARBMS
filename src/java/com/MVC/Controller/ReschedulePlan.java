/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
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
public class ReschedulePlan extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CAPDEVDAO capdevDAO = new CAPDEVDAO();

        int planID = Integer.parseInt(request.getParameter("planID"));
        int pointPersonID = Integer.parseInt(request.getParameter("pointPersonID"));
        double budgetSpent = Double.parseDouble(request.getParameter("budgetSpent"));
        double budgetRescheduled = Double.parseDouble(request.getParameter("budgetRescheduled"));

        java.sql.Date planDate = null;

        try {
            java.util.Date parsedPlanDate = sdf.parse(request.getParameter("planDate"));
            planDate = new java.sql.Date(parsedPlanDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AddCAPDEVPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(capdevDAO.reschedulePlan(planID, pointPersonID, budgetSpent, budgetRescheduled, planDate)){
            request.setAttribute("success", "Plan rescheduled successfully!");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in rescheduling plan. Try again.");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }

    }

}
