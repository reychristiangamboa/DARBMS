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
public class MonitorCAPDEVAttendance extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int planID = Integer.parseInt(request.getParameter("planID"));
        if (request.getParameter("requestID") != null) {
            int requestID = Integer.parseInt(request.getParameter("requestID"));
            request.setAttribute("requestID", requestID);
        }

        request.setAttribute("planID", planID);
        request.getRequestDispatcher("point-person-review-capdev-attendance.jsp").forward(request, response);

    }

}
