/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class AddCAPDEVPlan extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CAPDEVDAO capdevDAO = new CAPDEVDAO();

        String[] activities = request.getParameterValues("activities[]");
        String[] activityDates = request.getParameterValues("activityDates[]");

        CAPDEVPlan capdevPlan = new CAPDEVPlan();

        capdevPlan.setRequestID(Integer.parseInt(request.getParameter("requestID")));
        capdevPlan.setPlanDTN(request.getParameter("dtn"));
        
        
        java.sql.Date planDate = null;

            try {
                java.util.Date parsedPlanDate = sdf.parse(request.getParameter("planDate"));
                planDate = new java.sql.Date(parsedPlanDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(AddARBCrop.class.getName()).log(Level.SEVERE, null, ex);
            }

            capdevPlan.setPlanDate(planDate);

        int planID = 0;

        if (request.getParameter("pastDueID") != null) {
            capdevPlan.setPastDueAccountID(Integer.parseInt(request.getParameter("pastDueID")));
            planID = capdevDAO.addCAPDEVPlanForPastDue(capdevPlan, (Integer) session.getAttribute("userID"));
        } else {
            planID = capdevDAO.addCAPDEVPlan(capdevPlan, (Integer) session.getAttribute("userID"));
        }

        for (int i = 0; i < activities.length; i++) {

            CAPDEVActivity activity = new CAPDEVActivity();
            java.sql.Date activityDate = null;

            try {
                java.util.Date parsedActivityDate = sdf.parse(activityDates[i]);
                activityDate = new java.sql.Date(parsedActivityDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(SendCAPDEVProposal.class.getName()).log(Level.SEVERE, null, ex);
            }

            int activityType = Integer.parseInt(activities[i]);
            activity.setActivityType(activityType);
            activity.setPlanID(planID);
            
            int newlyAddedActivityID = capdevDAO.addCAPDEVPlanActivity(activity);

        }
        
        request.setAttribute("planID", planID);
        request.setAttribute("participants", true);
        request.getRequestDispatcher("PFO-CAPDEV-plan-activity-list.jsp").forward(request, response);
    }
}
