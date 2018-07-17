/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
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
public class PostponePlan extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int postponeReason = Integer.parseInt(request.getParameter("postponeReason"));
        int planID = Integer.parseInt(request.getParameter("planID"));
        String reason = request.getParameter("reason");
        
        CAPDEVDAO dao = new CAPDEVDAO();
        
        if(dao.postponePlan(planID, postponeReason, reason) && dao.updatePlanStatus(planID, 6)){ // UPDATES STATUS TO POSTPONED WITH REASON
            request.setAttribute("success", "Plan successfully postponed!");
            request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in postponing plan. Try again.");
            request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
        }
        
    }

    

}
