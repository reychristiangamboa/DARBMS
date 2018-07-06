/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
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
public class CheckAttendance extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CAPDEVDAO capdevDAO = new CAPDEVDAO();

        int planID = Integer.parseInt(request.getParameter("planID"));
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        String[] attendeesStr = request.getParameterValues("attendee");
        ArrayList<Integer> arbIDs = new ArrayList();

        for (String attendeeStr : attendeesStr) {
            arbIDs.add(Integer.parseInt(attendeeStr));
        }
        
        capdevDAO.setTechnicalAssistant(request.getParameter("TA"), activityID);

        if (capdevDAO.checkIfPresent(arbIDs, activityID)) { // PRESENT
            request.setAttribute("planID", planID);
            request.setAttribute("success", "Attendees recorded!");
            request.getRequestDispatcher("PP-CAPDEV-review-capdev-attendance.jsp").forward(request, response);
        }

    }

}
