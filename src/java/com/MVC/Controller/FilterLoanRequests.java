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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ijJPN
 */
public class FilterLoanRequests extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("selectAll") != null) { // IS CHECKED
            request.getRequestDispatcher("provincial-field-officer-view-loan-status.jsp").forward(request, response);
        } else {
            ArrayList<Integer> cityMunIDs = new ArrayList();
            String[] cityValStr = request.getParameterValues("cities[]");
            for (String id : cityValStr) {
                cityMunIDs.add(Integer.parseInt(id));
            }

            APCPRequestDAO dao = new APCPRequestDAO();

            ArrayList<APCPRequest> requestedRequests = dao.getAllCityMunRequestsByStatus(1, cityMunIDs);
            ArrayList<APCPRequest> clearedRequests = dao.getAllCityMunRequestsByStatus(2, cityMunIDs);
            ArrayList<APCPRequest> endorsedRequests = dao.getAllCityMunRequestsByStatus(3, cityMunIDs);
            ArrayList<APCPRequest> approvedRequests = dao.getAllCityMunRequestsByStatus(4, cityMunIDs);
            ArrayList<APCPRequest> releasedRequests = dao.getAllCityMunRequestsByStatus(5, cityMunIDs);
            ArrayList<APCPRequest> forReleaseRequests = dao.getAllCityMunRequestsByStatus(7, cityMunIDs);

            request.setAttribute("requested", requestedRequests);
            request.setAttribute("cleared", clearedRequests);
            request.setAttribute("endorsed", endorsedRequests);
            request.setAttribute("approved", approvedRequests);
            request.setAttribute("released", releasedRequests);
            request.setAttribute("forRelease", forReleaseRequests);
            request.getRequestDispatcher("provincial-field-officer-view-filtered-loan-status.jsp").forward(request, response);

        }

    }

}
