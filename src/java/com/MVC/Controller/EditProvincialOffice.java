/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
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
public class EditProvincialOffice extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AddressDAO dao = new AddressDAO();
        
        if(dao.editProvOffice(request.getParameter("branch"), Integer.parseInt(request.getParameter("regCode")), Integer.parseInt(request.getParameter("provOfficeCode")))){
            request.setAttribute("success", "Provincial Office updated!");
            request.getRequestDispatcher("admin-manage-provincial-offices.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in updating provincial office.");
            request.getRequestDispatcher("admin-manage-provincial-offices.jsp").forward(request, response);
        }
    }
}
