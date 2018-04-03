/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBODAO;
import com.MVC.Model.ARBO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class ViewARBO extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        ARBODAO arboDAO = new ARBODAO();
        ARBO arbo = new ARBO();
        arbo = arboDAO.getARBOByID(id);
        
        System.out.println(arbo.getArboName());
        
        request.setAttribute("arbo", arbo);
        request.getRequestDispatcher("arbo-profile.jsp").forward(request, response);
        
    }

    
}
