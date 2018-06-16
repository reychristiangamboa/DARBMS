/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Rey Christian
 */
public class SendCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ARBODAO arboDAO = new ARBODAO();
        ARBDAO arbDAO = new ARBDAO();

        boolean missing = false;

        String[] activities = request.getParameterValues("activities[]");
        String[] activityDates = request.getParameterValues("activityDates[]");

        if (files.length != activities.length) {
            request.setAttribute("errMessage", "Activity length does not match Participants length!");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("provincial-field-officer-create-capdev-proposal.jsp").forward(request, response);
        } else {
            ARBO arbo = arboDAO.getARBOByID(Integer.parseInt(request.getParameter("requestID")));
            ArrayList<ARB> allARBs = arbDAO.getAllARBsARBO(arbo.getArboID());

            CAPDEVPlan capdevPlan = new CAPDEVPlan();

            capdevPlan.setRequestID(Integer.parseInt(request.getParameter("requestID")));
            capdevPlan.setPlanDTN(request.getParameter("dtn"));

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
                activity.setActivityDate(activityDate);

                int newlyAddedActivityID = capdevDAO.addCAPDEVPlanActivity(activity);

            }

            request.setAttribute("success", "CAPDEV plan submitted!");
            request.getRequestDispatcher("PFO-CAPDEV-plan-activity-list.jsp").forward(request, response);

        }

    }

    

}
