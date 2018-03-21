/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.CAPDEVPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ijJPN
 */
public class FilterCAPDEVRequestsCCP extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("selectAll") != null) { // IS CHECKED
            request.getRequestDispatcher("provincial-field-officer-select-request.jsp").forward(request, response);
        } else {
            ArrayList<Integer> cityMunIDs = new ArrayList();
            String[] cityValStr = request.getParameterValues("cities[]");
            for (String id : cityValStr) {
                cityMunIDs.add(Integer.parseInt(id));
            }

            APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
            
            ArrayList<APCPRequest> requestedRequests = apcpRequestDAO.getAllCityMunRequestsByStatus(1, cityMunIDs);
            
            request.setAttribute("requested", requestedRequests);
            
            request.getRequestDispatcher("provincial-field-officer-filtered-select-request.jsp").forward(request, response);
        }

    }

}
