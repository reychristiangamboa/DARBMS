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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class ViewCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planID = Integer.parseInt(request.getParameter("planID"));
        int linksfarm = 0;
        
        if(request.getParameter("linksfarm") != null){
            linksfarm = 1;
        }
        CAPDEVDAO cap = new CAPDEVDAO();
        CAPDEVPlan plan = cap.getCAPDEVPlan(planID);
        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRequest req = dao.getRequestByID(plan.getRequestID());
        
        request.setAttribute("planID", planID);
        if (linksfarm > 0) {
            request.setAttribute("linksfarm", 1);
            request.setAttribute("planID", planID);
            request.getRequestDispatcher("regional-field-officer-view-linksfarm-capdev-plan.jsp").forward(request, response);
        } else {
            request.setAttribute("planID", planID);
            request.setAttribute("requestID", req.getRequestID());
            request.getRequestDispatcher("PFO-HEAD-view-capdev-plan.jsp").forward(request, response);
        }

    }

}
