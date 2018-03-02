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
public class EditPersonalInformation extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        User u = new User();
        UserDAO uDAO = new UserDAO();
        
        u.setFullName(request.getParameter("fullName"));
        u.setAddress(request.getParameter("address"));
        u.setContactNo(request.getParameter("contactNo"));
        int userID = (Integer) session.getAttribute("userID");
        
        if(uDAO.editPersonalInformation(u, userID)){
            request.setAttribute("success", "Personal information successfully updated!");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in updating your personal information. Try again.");
            request.getRequestDispatcher("edit-profile.jsp").forward(request, response);
        }
    }
    
}
