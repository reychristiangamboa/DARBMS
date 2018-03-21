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
public class ApproveCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        if(capdevDAO.updatePlanStatus(Integer.parseInt(request.getParameter("planID")), 2)){
            request.setAttribute("success", "CAPDEV Plan approved!");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in approving CAPDEV plan. Try again.");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }
    }

    
    
}
