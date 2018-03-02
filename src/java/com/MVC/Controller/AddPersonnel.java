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
import java.sql.SQLException;
import java.util.ArrayList;
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
public class AddPersonnel extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        LogDAO lDAO = new LogDAO();
        Log l = new Log();
        boolean emailExist = false;

        UserDAO uDAO = new UserDAO();
        ArrayList<User> allUsers = uDAO.getAllUsers();
        User u = new User();

        u.setFullName(request.getParameter("fullName"));
        u.setAddress(request.getParameter("address"));
        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        u.setContactNo(request.getParameter("contactNo"));
        
        u.setUserType(Integer.parseInt(request.getParameter("role")));
        u.setRegOfficeCode(Integer.parseInt(request.getParameter("region")));
        u.setProvOfficeCode(Integer.parseInt(request.getParameter("province")));
        

        for (User user : allUsers) {
            //  check if email exists
            if (u.getEmail().equals(user.getEmail())) {
                emailExist = true;
                request.setAttribute("errMessage", "Error in registering account. Email already exists or passwords do not match.");
                request.getRequestDispatcher("admin-add-personnel.jsp").forward(request, response);
            }
        }

        //  check if password match
        if (request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
            u.setPassword(request.getParameter("password"));
            try {
                //  check if sucessfully added
                if (uDAO.findUser(u.getEmail()) != null) {
                    request.setAttribute("errMessage", "Error in adding personnel. Email already exists or passwords do not match.");
                    request.getRequestDispatcher("admin-add-personnel.jsp").forward(request, response);
                } else {
                    if (uDAO.addPersonnel(u)) {
                        request.setAttribute("success", "Personnel added!");

                        l.setActionBy((Integer) session.getAttribute("userID"));
                        l.setActionType(3);
                        lDAO.addLog(l);

                        request.getRequestDispatcher("admin-add-personnel.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errMessage", "Error in adding personnel. Email already exists or passwords do not match.");
                        request.getRequestDispatcher("admin-add-personnel.jsp").forward(request, response);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddPersonnel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
