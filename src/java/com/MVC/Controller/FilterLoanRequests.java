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

        if (request.getParameter("filterBy").equals("All")) { // IS CHECKED
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        } else if(request.getParameter("filterBy").equals("provinces")){
            ArrayList<Integer> provinceIDs = new ArrayList();
            String[] provinceValStr = request.getParameterValues("provinces[]");
            for (String id : provinceValStr) {
                provinceIDs.add(Integer.parseInt(id));
            }

            APCPRequestDAO dao = new APCPRequestDAO();

            ArrayList<APCPRequest> requestedRequests = dao.getAllProvincialRequestsByStatus(1, provinceIDs);
            ArrayList<APCPRequest> clearedRequests = dao.getAllProvincialRequestsByStatus(2, provinceIDs);
            ArrayList<APCPRequest> endorsedRequests = dao.getAllProvincialRequestsByStatus(3, provinceIDs);
            ArrayList<APCPRequest> approvedRequests = dao.getAllProvincialRequestsByStatus(4, provinceIDs);
            ArrayList<APCPRequest> releasedRequests = dao.getAllProvincialRequestsByStatus(5, provinceIDs);
            ArrayList<APCPRequest> forReleaseRequests = dao.getAllProvincialRequestsByStatus(7, provinceIDs);

            request.setAttribute("requested", requestedRequests);
            request.setAttribute("cleared", clearedRequests);
            request.setAttribute("endorsed", endorsedRequests);
            request.setAttribute("approved", approvedRequests);
            request.setAttribute("released", releasedRequests);
            request.setAttribute("forRelease", forReleaseRequests);
            request.getRequestDispatcher("view-filtered-apcp-status.jsp").forward(request, response);
        
        }  else if(request.getParameter("filterBy").equals("regions")){
            ArrayList<Integer> regionIDs = new ArrayList();
            String[] regionValStr = request.getParameterValues("regions[]");
            for (String id : regionValStr) {
                regionIDs.add(Integer.parseInt(id));
            }

            APCPRequestDAO dao = new APCPRequestDAO();

            ArrayList<APCPRequest> requestedRequests = dao.getAllRegionalRequestsByStatus(1, regionIDs);
            ArrayList<APCPRequest> clearedRequests = dao.getAllRegionalRequestsByStatus(2, regionIDs);
            ArrayList<APCPRequest> endorsedRequests = dao.getAllRegionalRequestsByStatus(3, regionIDs);
            ArrayList<APCPRequest> approvedRequests = dao.getAllRegionalRequestsByStatus(4, regionIDs);
            ArrayList<APCPRequest> releasedRequests = dao.getAllRegionalRequestsByStatus(5, regionIDs);
            ArrayList<APCPRequest> forReleaseRequests = dao.getAllRegionalRequestsByStatus(7, regionIDs);

            request.setAttribute("requested", requestedRequests);
            request.setAttribute("cleared", clearedRequests);
            request.setAttribute("endorsed", endorsedRequests);
            request.setAttribute("approved", approvedRequests);
            request.setAttribute("released", releasedRequests);
            request.setAttribute("forRelease", forReleaseRequests);
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        }

    }

}
