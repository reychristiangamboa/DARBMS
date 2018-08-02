/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.DAO.IssueDAO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.Issue;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class CheckAttendance extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        IssueDAO issueDAO = new IssueDAO();
        CAPDEVActivity activity = new CAPDEVActivity();
        HttpSession session = request.getSession();

        int planID = Integer.parseInt(request.getParameter("planID"));
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        String[] attendeesStr = request.getParameterValues("attendee");

        activity = capdevDAO.getCAPDEVPlanActivityById(activityID);

        if (activity.getActivityCategory() == 1) { // MANDATORY
            if (activity.getArbList().size() != attendeesStr.length) { // IF PARTICIPANTS SIZE NOT EQUAL TO ATTENDEES SIZE, RAISE AN ISSUE
                // SOMEONE'S ABSENT
                request.setAttribute("errMessage", " An issue has been raised! Absentees detected during a MANDATORY activity.");
                
                Issue i = new Issue();
                i.setIssueType(2); // MANDATORY TRAINING ABSENCE
                i.setIssuedTo(8); // PP-CAPDEV
                i.setIssuedBy((Integer)session.getAttribute("userID"));
                i.setProvOfficeCode((Integer)session.getAttribute("provOfficeCode"));
                i.setPlanID(activityID); // ACTIVITY ID, FOR EASIER UPDATE OF ATTENDANCE
                
                if(issueDAO.raiseIssue(i)){
                    System.out.println("ISSUE RAISED!");
                }
                
            }else{
                request.setAttribute("success", "Attendees recorded!");
            }
        }

        ArrayList<Integer> arbIDs = new ArrayList();

        for (String attendeeStr : attendeesStr) {
            arbIDs.add(Integer.parseInt(attendeeStr));
        }

        capdevDAO.setTechnicalAssistant(request.getParameter("TA"), activityID);

        if (capdevDAO.checkIfPresent(arbIDs, activityID)) { // PRESENT
            request.setAttribute("planID", planID);
            request.getRequestDispatcher("PP-CAPDEV-review-capdev-attendance.jsp").forward(request, response);
        }

    }

}
