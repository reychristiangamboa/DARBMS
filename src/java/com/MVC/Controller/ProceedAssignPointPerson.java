/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

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
public class ProceedAssignPointPerson extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planID = Integer.parseInt(request.getParameter("planID"));
        if(request.getParameter("linksfarm") != null){
            request.setAttribute("linksfarm", 1);
        }
        request.setAttribute("planID", planID);
        request.getRequestDispatcher("provincial-field-officer-approved-capdev-proposals.jsp").forward(request, response);
    }

}
