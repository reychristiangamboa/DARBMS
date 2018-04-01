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
public class FilterCAPDEVRequests extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("filterBy").equals("All")) { // IS CHECKED
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
        
        } else if(request.getParameter("filterBy").equals("provinces")){
            ArrayList<Integer> provinceIDs = new ArrayList();
            String[] provinceValStr = request.getParameterValues("provinces[]");
            for (String id : provinceValStr) {
                provinceIDs.add(Integer.parseInt(id));
            }

            CAPDEVDAO capdevDAO = new CAPDEVDAO();
            APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();

            ArrayList<CAPDEVPlan> pendingPlans = capdevDAO.getAllProvincialCAPDEVPlanByStatus(1, provinceIDs);
            ArrayList<CAPDEVPlan> approvedPlans = capdevDAO.getAllProvincialCAPDEVPlanByStatus(2, provinceIDs);
            ArrayList<CAPDEVPlan> disapprovedPlans = capdevDAO.getAllProvincialCAPDEVPlanByStatus(3, provinceIDs);
            ArrayList<CAPDEVPlan> implementedPlans = capdevDAO.getAllProvincialCAPDEVPlanByStatus(5, provinceIDs);
            
            ArrayList<APCPRequest> requestedRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(1, provinceIDs);
            
            request.setAttribute("requested", requestedRequests);
            request.setAttribute("pending", pendingPlans);
            request.setAttribute("approved", approvedPlans);
            request.setAttribute("implemented", implementedPlans);
            request.setAttribute("disapproved", disapprovedPlans);
            
            request.getRequestDispatcher("view-filtered-capdev-status.jsp").forward(request, response);
        
        } else if(request.getParameter("filterBy").equals("regions")){
            ArrayList<Integer> regionIDs = new ArrayList();
            String[] regionValStr = request.getParameterValues("regions[]");
            for (String id : regionValStr) {
                regionIDs.add(Integer.parseInt(id));
            }

            CAPDEVDAO capdevDAO = new CAPDEVDAO();
            APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();

            ArrayList<CAPDEVPlan> pendingPlans = capdevDAO.getAllRegionalCAPDEVPlanByStatus(1, regionIDs);
            ArrayList<CAPDEVPlan> approvedPlans = capdevDAO.getAllRegionalCAPDEVPlanByStatus(2, regionIDs);
            ArrayList<CAPDEVPlan> disapprovedPlans = capdevDAO.getAllRegionalCAPDEVPlanByStatus(3, regionIDs);
            ArrayList<CAPDEVPlan> implementedPlans = capdevDAO.getAllRegionalCAPDEVPlanByStatus(5, regionIDs);
            
            ArrayList<APCPRequest> requestedRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(1, regionIDs);
            
            request.setAttribute("requested", requestedRequests);
            request.setAttribute("pending", pendingPlans);
            request.setAttribute("approved", approvedPlans);
            request.setAttribute("implemented", implementedPlans);
            request.setAttribute("disapproved", disapprovedPlans);
            
            request.getRequestDispatcher("view-filtered-capdev-status.jsp").forward(request, response);
        }

    }

}
