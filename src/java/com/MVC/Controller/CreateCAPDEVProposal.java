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
 * @author Rey Christian
 */
public class CreateCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("requestID", id);
        
        if (request.getParameter("pastDueID") != null) {
            request.setAttribute("pastDueID", Integer.parseInt(request.getParameter("pastDueID")));
        }

        request.getRequestDispatcher("PFO-CAPDEV-create-capdev-proposal.jsp").forward(request, response);
    }

}
