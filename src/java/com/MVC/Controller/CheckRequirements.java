/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class CheckRequirements extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int requestID = Integer.parseInt(request.getParameter("id"));
        APCPRequestDAO requestDAO = new APCPRequestDAO();
        APCPRequest req = requestDAO.getRequestByID(requestID);
        
        
        if (checkARBOHadAPCPOrientation(req.getPlans())) { // REQUEST have plans that had APCP Orientation
            request.setAttribute("requestID", requestID);
            request.getRequestDispatcher("view-apcp-application.jsp").forward(request, response);
        } else {
            request.setAttribute("notice", "APCP Request requires APCP Orientation.");
            request.getRequestDispatcher("").forward(request, response);
        }

    }

    private boolean checkARBOHadAPCPOrientation(ArrayList<CAPDEVPlan> planList) {
        for (CAPDEVPlan plan : planList) {
            for (CAPDEVActivity act : plan.getActivities()) {
                if (plan.getPlanStatus() == 5 && act.getActivityType() == 2) { // IMPLEMENTED and APCP ORIENTATION
                    return true;
                }
            }
        }
        return false;
    }

}
