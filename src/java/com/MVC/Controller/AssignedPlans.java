/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.CalendarEvent;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class AssignedPlans extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        HttpSession session = request.getSession();
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        final String RED = "#d9534f";
        final String GREEN = "#5cb85c";
        
        List l = new ArrayList();
        
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ArrayList<CAPDEVPlan> assignedPlans = capdevDAO.getAssignedRequestCAPDEVPlans((Integer)session.getAttribute("userID"));
        System.out.println(assignedPlans.size());
        
        String id = "";
        
        for(CAPDEVPlan plan : assignedPlans){
            plan.setActivities(capdevDAO.getCAPDEVPlanActivities(plan.getPlanID()));
            for(CAPDEVActivity activity : plan.getActivities()){
                
                id = plan.getPlanID() + "" + activity.getActivityID();
                
                CalendarEvent e = new CalendarEvent();
                
                e.setId(activity.getActivityID());
                
                e.setTitle(plan.getPlanDTN() + ": " + activity.getActivityName());
                e.setDescription(activity.getActivityDesc());
                e.setUrl("MonitorCAPDEVAttendance?planID="+plan.getPlanID() );
                e.setStart(f.format(plan.getPlanDate()));
                e.setColor(GREEN);
                
                l.add(e);
            }
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(new Gson().toJson(l));
        out.write(new Gson().toJson(l));
    }

    

}
