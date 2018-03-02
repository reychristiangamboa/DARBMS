/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBODAO;
import com.MVC.Model.ARBO;
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
public class ARBONameRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ARBODAO arboDAO = new ARBODAO();

        ARBO arbo = new ARBO();

        try (PrintWriter out = response.getWriter()) {
            String valajax = request.getParameter("valajax");
            arbo = arboDAO.getARBOByID(Integer.parseInt(valajax));

            response.getWriter().write("<label for=''>Name of ARBO</label>");
            response.getWriter().write("<div class='input-group'>");
            response.getWriter().write("<input type='text' value='" + arbo.getArboName() + "' class='form-control' disabled>");
            response.getWriter().write("<input type='hidden' value='" + arbo.getArboID() + "' name='arboID'");
            response.getWriter().write("<span class='input-group-btn'>");
            response.getWriter().write("<button type='button' class='btn btn-success' data-toggle='modal' data-target='#modal-default'>Select ARBO</button>");
            response.getWriter().write("</span>");
            response.getWriter().write("</div>");

        }
    }

}
