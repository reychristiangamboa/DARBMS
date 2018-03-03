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
 * @author ijJPN
 */
public class SendCAPDEVAssessment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int planID = Integer.parseInt(request.getParameter("planID"));
        CAPDEVDAO dao = new CAPDEVDAO();
        
        if(dao.updatePlanStatus(planID, 5)){
            request.setAttribute("success", "CAPDEV Assessment sent!");
            request.getRequestDispatcher("point-person-view-capdev-plans.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in sending CAPDEV assessment.");
            request.getRequestDispatcher("point-person-officer-view-capdev-plans.jsp").forward(request, response);
        }
        
        
    }

    

}
