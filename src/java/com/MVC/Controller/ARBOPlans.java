/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.ARBO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import com.MVC.Model.CalendarEvent;
import com.MVC.Model.User;
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
public class ARBOPlans extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        int arboID = Integer.parseInt(request.getParameter("id"));
        ARBODAO dao = new ARBODAO();
        ARBO arbo = dao.getARBOByID(arboID);
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        UserDAO userDAO = new UserDAO();

        ArrayList<CAPDEVPlan> arboPlans = new ArrayList();

        for (APCPRequest req : arbo.getRequestList()) {
            for (CAPDEVPlan plan : req.getPlans()) {
                arboPlans.add(plan);
            }
        }

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        final String RED = "#d9534f";
        final String GREEN = "#5cb85c";

        List l = new ArrayList();

        String id = "";

        for (CAPDEVPlan plan : arboPlans) {
            if (plan.getPlanStatus() == 1 || plan.getPlanStatus() == 2 || plan.getPlanStatus() == 4) {
                User u = userDAO.searchUser(plan.getAssignedTo());
                plan.setActivities(capdevDAO.getCAPDEVPlanActivities(plan.getPlanID()));
                for (CAPDEVActivity activity : plan.getActivities()) {
                    
                    id = plan.getPlanID() + "" + activity.getActivityID();

                    CalendarEvent e = new CalendarEvent();

                    e.setId(activity.getActivityID());

                    e.setTitle(plan.getPlanDTN() + ": " + activity.getActivityName());
                    e.setDescription("Assigned To: " + u.getFullName());

                    e.setStart(f.format(plan.getPlanDate()));
                    e.setColor(GREEN);

                    l.add(e);
                }
            }

        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(new Gson().toJson(l));
        out.write(new Gson().toJson(l));
    }

}
