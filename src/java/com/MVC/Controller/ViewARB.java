/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.Model.ARB;
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
public class ViewARB extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        ARBDAO dao = new ARBDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        ARB arb = dao.getARBByID(id);
        
        request.setAttribute("arb", arb);
        request.getRequestDispatcher("arb-profile.jsp").forward(request, response);
        
    }

    

}
