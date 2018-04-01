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
public class DisapproveCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        int linksfarm = 0;

        if (request.getParameter("linksfarm") != null) {
            linksfarm = Integer.parseInt(request.getParameter("linksfarm"));
        }

        if (capdevDAO.updatePlanStatus(Integer.parseInt(request.getParameter("planID")), 3)) {
            if (linksfarm > 0) {
                request.setAttribute("errMessage", "CAPDEV Plan disapproved!");
                request.getRequestDispatcher("view-linksfarm-capdev-status.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "CAPDEV Plan disapproved!");
                request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
            }

        } else {
            if (linksfarm > 0) {
                request.setAttribute("errMessage", "Error in disapproving CAPDEV Plan.");
                request.getRequestDispatcher("view-linksfarm-capdev-status.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in disapproving CAPDEV Plan.");
                request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
            }
        }
    }

    

}
