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
public class SelectARBORequest extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ARBODAO dao = new ARBODAO();
        int id = Integer.parseInt(request.getParameter("id"));

        ARBO arbo = dao.getARBOByID(id);

        request.setAttribute("arboID", id);

        if (arbo.getAPCPQualified() == 0) {
            request.getRequestDispatcher("provincial-field-officer-request-apcp-access.jsp").forward(request, response);
            request.setAttribute("newAccessing", true);
        } else {
            request.getRequestDispatcher("provincial-field-officer-request-loan.jsp").forward(request, response);
        }

    }

}
