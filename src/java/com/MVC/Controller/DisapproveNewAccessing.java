/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.Model.APCPRequest;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ijJPN
 */
public class DisapproveNewAccessing extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int requestID = Integer.parseInt(request.getParameter("id"));

        ARBODAO aDAO = new ARBODAO();
        APCPRequestDAO rDAO = new APCPRequestDAO();

        APCPRequest r = rDAO.getRequestByID(requestID);

        if (aDAO.updateARBOStatus(r.getArboID(), 0) && rDAO.updateRequestStatus(requestID, 7)) {
            request.setAttribute("errMessage", "Access to APCP disapproved!");
            request.getRequestDispatcher("central-view-new-requesting-arbo.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in disapproving access to APCP.");
            request.getRequestDispatcher("central-view-new-requesting-arbo.jsp").forward(request, response);
        }

    }

}
