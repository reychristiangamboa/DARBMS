/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.LogDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.Log;
import com.MVC.Model.User;
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
public class Login extends BaseServlet {

    protected void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        UserDAO uDAO = new UserDAO();

        User tempUser = new User();
        tempUser.setEmail(request.getParameter("email"));
        tempUser.setPassword(request.getParameter("password"));

        User u = uDAO.findUser(tempUser.getEmail());

        if (u != null) {
            if (uDAO.loginUser(tempUser)) {
                //  SETTING OF SESSION ATTRIBUTES = PERSONAL INFO
                session.setAttribute("login", "success");
                session.setAttribute("userID", u.getUserID());
                session.setAttribute("email", u.getEmail());
                session.setAttribute("password", u.getPassword());
                session.setAttribute("fullName", u.getFullName());
                session.setAttribute("address", u.getAddress());
                session.setAttribute("contactNo", u.getContactNo());
                session.setAttribute("userType", u.getUserType());
                session.setAttribute("userTypeDesc", u.getUserTypeDesc());
                session.setAttribute("regOfficeCode", u.getRegOfficeCode());
                session.setAttribute("regOfficeDesc", u.getRegDesc());
                session.setAttribute("provOfficeCode", u.getProvOfficeCode());
                session.setAttribute("provOfficeDesc", u.getProvOfficeDesc());

                if (u.getActive() == 1) {   //  ACTIVATED ACCOUNTS
                    System.out.println(u.getEmail());
                    LogDAO lDAO = new LogDAO();
                    Log l = new Log();
                    l.setActionBy((Integer) session.getAttribute("userID"));
                    l.setActionType(1); //  LOGIN
                    lDAO.addLog(l);

                    if (u.getUserType() == 1) {   // ADMINISTRATOR
                        request.getRequestDispatcher("admin-system-logs.jsp").forward(request, response);
                    } else if (u.getUserType() == 2) {   //  POINT PERSON APCP
                        request.getRequestDispatcher("PP-APCP-view-apcp-requests.jsp").forward(request, response);
                    } else if (u.getUserType() == 3) {   //  PROVINCIAL FIELD OFFICER
                        request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
                    } else if (u.getUserType() == 4) {   //  REGIONAL FIELD OFFICER
                        request.getRequestDispatcher("RFO-home.jsp").forward(request, response);
                    } else if (u.getUserType() == 5) {   //  CENTRAL OFFICER
                        request.getRequestDispatcher("apcp-dashboard.jsp").forward(request, response);
                    } else if (u.getUserType() == 6) {   //  PFO-APCP
                        request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
                    } else if (u.getUserType() == 7) {   //  PFO-CAPDEV
                        request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
                    } else if (u.getUserType() == 8) {   //  POINT PERSON CAPDEV
                        request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
                    }

                } else {    //  DEACTIVATED ACCOUNTS
                    request.getRequestDispatcher("account-deactivated.jsp").forward(request, response);
                }
            } else {    //  NO USER EXISTS
                request.setAttribute("error", "Invalid username or password. Try again.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } else {    //  NO USER EXISTS
            request.setAttribute("error", "Invalid username or password. Try again.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
