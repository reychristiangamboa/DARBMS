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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ijJPN
 */
public class AssignPointPerson extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int planID = Integer.parseInt(request.getParameter("planID"));
        int pointPersonID = Integer.parseInt(request.getParameter("pointPersonID"));
        
        CAPDEVDAO dao = new CAPDEVDAO();
        
        if(dao.assignPointPerson(planID, pointPersonID) && dao.updatePlanStatus(planID, 4)){
            request.setAttribute("success", "Point Person successfully assigned to CAPDEV Plan!");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in assigning Point Person to CAPDEV Plan.");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }
    }

    

}
