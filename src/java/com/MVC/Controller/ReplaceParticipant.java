/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.ARB;
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
public class ReplaceParticipant extends BaseServlet {
    
    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ARBDAO arbDAO = new ARBDAO();
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        int planID = Integer.parseInt(request.getParameter("planID"));
        
        String[] participantsStr = request.getParameterValues("attendee");
        String[] nonParticipantsStr = request.getParameterValues("replacee");
        
        ArrayList<Integer> participants = new ArrayList();
        
        for (String participantStr : participantsStr) {
            participants.add(Integer.parseInt(participantStr));
        }
        
        ArrayList<ARB> nonParticipants = new ArrayList();
        ArrayList<Integer> nonParticipantsID = new ArrayList();
        
        for (String nonParticipantStr : nonParticipantsStr) {
            ARB arb = arbDAO.getARBByID(Integer.parseInt(nonParticipantStr)); // recognize ARB
            nonParticipantsID.add(Integer.parseInt(nonParticipantStr)); // add IDs
            
            nonParticipants.add(arb); // add ARB for adding as participant
            
        }
        
        if (participantsStr.length != nonParticipantsStr.length) {
            request.setAttribute("planID", planID);
            request.setAttribute("errMessage", "Attendees and Replacees don't match. Try again.");
            request.getRequestDispatcher("PP-CAPDEV-review-capdev-attendance.jsp").forward(request, response);
        } else {
            capdevDAO.replaceAttendees(participants, activityID);
            if (capdevDAO.addCAPDEVPlanActivityParticipants(nonParticipants, activityID) && capdevDAO.checkIfPresent(nonParticipantsID, activityID)) {
                request.setAttribute("planID", planID);
                request.setAttribute("success", "Attendees replaced!");
                request.getRequestDispatcher("PP-CAPDEV-review-capdev-attendance.jsp").forward(request, response);
            }
        }
    }
    
}
