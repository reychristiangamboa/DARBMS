/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.MessageDAO;
import com.MVC.Model.Message;
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
public class SendMessage extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        MessageDAO mDAO = new MessageDAO();
        Message message = new Message();
        
        message.setSentBy((Integer)session.getAttribute("userID"));
        message.setBody(request.getParameter("message"));
        int messageID = mDAO.addMessage(message);
        
        if(mDAO.sendMessage(messageID, Integer.parseInt(request.getParameter("sendTo")))){
            request.setAttribute("success", "Message sent successfully!");
            request.getRequestDispatcher("view-messages.jsp").forward(request, response);
        }
        
    }

    

}
