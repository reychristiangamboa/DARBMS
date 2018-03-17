/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.UserDAO;
import com.MVC.Model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class EditAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        User u = new User();
        UserDAO uDAO = new UserDAO();

        u.setEmail(request.getParameter("email"));
        u.setPassword(request.getParameter("password"));
        int userID = (Integer) session.getAttribute("userID");

        if (!request.getParameter("confirmPassword").equalsIgnoreCase(request.getParameter("password"))) {
            request.setAttribute("errMessage", "Passwords do no match. Try again");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
        }

        if (uDAO.editAccount(u, userID)) {

            session.setAttribute("email", u.getEmail());
            session.setAttribute("password", u.getPassword());

            request.setAttribute("success", "Account successfully updated!");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in updating your account. Try again.");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
        }
    }

}
