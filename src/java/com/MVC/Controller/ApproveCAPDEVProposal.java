/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.MessageDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class ApproveCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        HttpSession session = request.getSession();
        MessageDAO messageDAO = new MessageDAO();
        UserDAO userDAO = new UserDAO();

        if (capdevDAO.updatePlanStatus(Integer.parseInt(request.getParameter("planID")), 2)) {

            Message m = new Message();
            m.setBody("[RE:CAPDEV PLAN ASSIGNMENT] A new CAPDEV Plan has been assigned to you! Please refer to your calendar.");
            m.setSentBy((Integer) session.getAttribute("userID"));
            int messageID = messageDAO.addMessage(m);

            CAPDEVPlan plan = capdevDAO.getCAPDEVPlan(Integer.parseInt(request.getParameter("planID")));
            int userID = plan.getAssignedTo();

            if (messageDAO.sendMessage(messageID, userID)) {
                System.out.println("Message sent!");
            }

            request.setAttribute("success", "CAPDEV Plan approved!");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);

        } else {
            request.setAttribute("errMessage", "Error in approving CAPDEV plan. Try again.");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        }
    }

}
