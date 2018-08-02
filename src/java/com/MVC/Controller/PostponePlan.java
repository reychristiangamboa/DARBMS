/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.MessageDAO;
import com.MVC.DAO.UserDAO;
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
public class PostponePlan extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int postponeReason = Integer.parseInt(request.getParameter("postponeReason"));
        int planID = Integer.parseInt(request.getParameter("planID"));
        String reason = request.getParameter("reason");

        CAPDEVDAO dao = new CAPDEVDAO();

        if (dao.postponePlan(planID, postponeReason, reason) && dao.updatePlanStatus(planID, 6)) { // UPDATES STATUS TO POSTPONED WITH REASON

            HttpSession session = request.getSession();
            MessageDAO messageDAO = new MessageDAO();
            UserDAO userDAO = new UserDAO();

            Message m = new Message();
            m.setBody("[RE:CAPDEV PLAN POSTPONED] CAPDEV Plan #" + planID + " has been postponed due to " + reason + ". Please refer to POSTPONED PLANS.");
            m.setSentBy((Integer) session.getAttribute("userID"));
            int messageID = messageDAO.addMessage(m);

            ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(3, (Integer) session.getAttribute("provOfficeCode")); // PFO-HEAD

            for (int userID : userIDs) {
                if (messageDAO.sendMessage(messageID, userID)) {
                    System.out.println("Message sent!");
                }
            }

            request.setAttribute("success", "Plan successfully postponed!");
            request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in postponing plan. Try again.");
            request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
        }

    }

}
