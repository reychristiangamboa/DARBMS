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
                session.setAttribute("provOfficeCode", u.getProvOfficeCode());

                if (u.getActive() == 1) {   //  ACTIVATED ACCOUNTS

                    LogDAO lDAO = new LogDAO();
                    Log l = new Log();
                    l.setActionBy((Integer) session.getAttribute("userID"));
                    l.setActionType(1); //  LOGIN
                    lDAO.addLog(l);

                    if (u.getUserType() == 1) {   //  ADMINISTRATOR
                        request.getRequestDispatcher("admin-system-logs.jsp").forward(request, response);
                    } else if (u.getUserType() == 2) {   //  TECHNICAL ASSISTANT
                        request.getRequestDispatcher("technical-assistant-view-capdev-plans.jsp").forward(request, response);
                    } else if (u.getUserType() == 3) {   //  PROVINCIAL FIELD OFFICER
                        request.getRequestDispatcher("provincial-field-officer-arbo-list.jsp").forward(request, response);
                    } else if (u.getUserType() == 4) {   //  PROVINCIAL FIELD OFFICER
                        request.getRequestDispatcher("regional-field-officer-home.jsp").forward(request, response);
                    } else if (u.getUserType() == 5) {   //  PROVINCIAL FIELD OFFICER
                        request.getRequestDispatcher("central-home.jsp").forward(request, response);
//                if (u.getUserType() == 1) { // APPLICANT
//                    User us = uDAO.findCompany(u.getUserID());
//                    session.setAttribute("companyID", us.getCompany().getCompanyID());
//                    session.setAttribute("companyName", us.getCompany().getCompanyName());
//                    session.setAttribute("companyUnitNumAndStreet", us.getCompany().getCompanyUnitNumAndStreet());
//                    session.setAttribute("companyDistrict", us.getCompany().getCompanyDistrict());
//                    session.setAttribute("companyCity", us.getCompany().getCompanyCity());
//                    session.setAttribute("companyTelNo", us.getCompany().getCompanyTelNo());
//                    session.setAttribute("ownerName", us.getCompany().getOwnerName());
//                    session.setAttribute("ownerUnitNumAndStreet", us.getCompany().getOwnerUnitNumAndStreet());
//                    session.setAttribute("ownerDistrict", us.getCompany().getOwnerDistrict());
//                    session.setAttribute("ownerCity", us.getCompany().getOwnerCity());
//                    session.setAttribute("ownerTelNo", us.getCompany().getOwnerTelNo());
//                    session.setAttribute("companyAddress", us.getCompany().getCompanyAddress());
//                    session.setAttribute("ownerAddress", us.getCompany().getOwnerAddress());
//                    request.getRequestDispatcher("applicant-apply-lto.jsp").forward(request, response);
//                } else if (u.getUserType() == 2) {  // EVALUATOR 
//                    request.getRequestDispatcher("evaluator-assigned-applications.jsp").forward(request, response);
//                } else if (u.getUserType() == 3) {  //  INSPECTOR
//                    request.getRequestDispatcher("inspector-assigned-applications.jsp").forward(request, response);
//                } else if (u.getUserType() == 4) {  // DIVISION CHIEF
//                    request.getRequestDispatcher("division-chief-submitted-applications.jsp").forward(request, response);
//                } else if (u.getUserType() == 5) {  // DIRECTOR
//                    request.getRequestDispatcher("director-endorsed-applications.jsp").forward(request, response);
//                } else if (u.getUserType() == 6) {  // ADMIN
//                    request.getRequestDispatcher("admin-view-system-logs.jsp").forward(request, response);
//                }
                    } else {    //  DEACTIVATED ACCOUNTS
                        request.getRequestDispatcher("account-deactivated.jsp").forward(request, response);
                    }

                } else {    //  LOGIN FAIL
                    request.setAttribute("error", "Invalid username or password. Try again.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else {    //  NO USER EXISTS
                request.setAttribute("error", "Invalid username or password. Try again.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        }
    }
}