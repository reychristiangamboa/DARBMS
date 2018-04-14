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
public class ProceedAddARB extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int arboID = Integer.parseInt(request.getParameter("id"));
        String source = request.getParameter("source");
        
        if(source.equalsIgnoreCase("import")){
            request.setAttribute("source", "import");
        }else if(source.equalsIgnoreCase("profile")){
            request.setAttribute("source", "profile");
        }
        
        ARBODAO arboDAO = new ARBODAO();
        ARBO arbo = new ARBO();
        arbo = arboDAO.getARBOByID(arboID);
        
        request.setAttribute("arbo", arbo);
        request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
        
    }

    

}
