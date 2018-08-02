/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRequest;
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
public class RefreshARBRepayment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        APCPRequestDAO dao = new APCPRequestDAO();
        int arbID = Integer.parseInt(request.getParameter("arbID"));
        APCPRequest apcp = dao.getRequestByID(Integer.parseInt(request.getParameter("requestID")));
        double osBalance = apcp.getTotalARBOSBalance(arbID);
        
        System.out.println("O/S BALANCE: "+osBalance);

        try (PrintWriter out = response.getWriter()) {
            
            response.getWriter().write("<div class='form-group'>");
            response.getWriter().write("<label for=''>O/S Balance</label>");
            response.getWriter().write("<input type='text' class='form-control numberOnly' name='arbRepaymentOSBalance' value='"+osBalance+"' disabled/>");
            response.getWriter().write("</div>");
            
        }
    }

    

}
