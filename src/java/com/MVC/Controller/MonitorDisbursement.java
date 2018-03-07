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
public class MonitorDisbursement extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int releaseID = Integer.parseInt(request.getParameter("releaseID"));
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        request.setAttribute("releaseID", releaseID);
        request.setAttribute("requestID", requestID);
        request.getRequestDispatcher("point-person-monitor-disbursement.jsp").forward(request, response);
    }
    
}
