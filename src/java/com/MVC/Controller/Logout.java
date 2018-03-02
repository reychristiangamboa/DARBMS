/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.LogDAO;
import com.MVC.Model.Log;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class Logout extends BaseServlet {

    protected void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if ((Integer) session.getAttribute("userID") == null) {
            response.sendRedirect("index.jsp");
        } else {
            LogDAO lDAO = new LogDAO();
            Log l = new Log();
            l.setActionBy((Integer) session.getAttribute("userID"));
            l.setActionType(2);
            lDAO.addLog(l);
            session.removeAttribute("userID");
            session.invalidate();
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }
}
