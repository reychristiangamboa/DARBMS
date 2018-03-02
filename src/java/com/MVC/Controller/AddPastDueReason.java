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
public class AddPastDueReason extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CAPDEVDAO dao = new CAPDEVDAO();
        
        if(dao.addPastDueReason(request.getParameter("reason"))){
            request.setAttribute("success", "Past Due reason added!");
            request.getRequestDispatcher("provincial-field-officer-manage-past-due-reasons.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in adding reason for past due.");
            request.getRequestDispatcher("provincial-field-officer-manage-past-due-reasons.jsp").forward(request, response);
        }
        
    }
}
