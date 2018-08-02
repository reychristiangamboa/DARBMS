/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.MessageDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Rey Christian
 */
public class SendCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        MessageDAO messageDAO = new MessageDAO();
        UserDAO userDAO = new UserDAO();

        Message m = new Message();
        m.setBody("[RE:CAPDEV PLAN APPROVAL] A new CAPDEV Plan has been CREATED! Seeking for PFO-HEAD Approval.");
        m.setSentBy((Integer) session.getAttribute("userID"));
        int messageID = messageDAO.addMessage(m);

        ArrayList<Integer> userIDs = userDAO.retrieveProvincialUserIDsByUserType(3, (Integer) session.getAttribute("provOfficeCode")); // PFO-HEAD

        for (int userID : userIDs) {
            if (messageDAO.sendMessage(messageID, userID)) {
                System.out.println("Message sent!");
            }
        }

        request.setAttribute("success", "CAPDEV Plan submitted!");
        request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);

    }

}
