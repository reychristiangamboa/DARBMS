/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.LogDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.Log;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class DeactivateAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");

        UserDAO uDAO = new UserDAO();

        String[] userIDchecked = request.getParameterValues("activeCheckBox");

        try {

            uDAO.deactivateAccount(userIDchecked);

            LogDAO logDAO = new LogDAO();
            Log l = new Log();
            l.setActionType(5);
            l.setActionBy((Integer) session.getAttribute("userID"));
            logDAO.addLog(l);
            
        } catch (SQLException ex) {
            Logger.getLogger(DeactivateAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher("admin/admin-activate-deactivate-user.jsp").forward(request, response);
    }
}
