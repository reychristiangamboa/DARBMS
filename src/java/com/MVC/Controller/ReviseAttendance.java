/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.CAPDEVActivity;
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
public class ReviseAttendance extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CAPDEVDAO dao = new CAPDEVDAO();
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        CAPDEVActivity activity = dao.getCAPDEVPlanActivityById(activityID);
        String[] attendeesStr = request.getParameterValues("attendee");
        ArrayList<Integer> arbIDs = new ArrayList();
        
        for (String attendeeStr : attendeesStr) {
            arbIDs.add(Integer.parseInt(attendeeStr));
        }
        
        if (dao.checkIfPresent(arbIDs, activityID)) { // PRESENT
            request.setAttribute("success", activity.getActivityName() + " attendance revised successfully!");
            request.getRequestDispatcher("view-issues.jsp").forward(request, response);
        }
        
        
    }

    

}
