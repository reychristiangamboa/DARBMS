/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Z40
 */
public class ViewNonExistingSite extends BaseServlet {


    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
 
        int cityMunCode = id;

        request.setAttribute("cityMunCode", cityMunCode);
        request.getRequestDispatcher("provincial-field-officer-view-new-project-site.jsp").forward(request, response);
        
    }
}