/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.servlet.ServletException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ijJPN
 */
public class SendCAPDEVAssessment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int planID = Integer.parseInt(request.getParameter("planID"));
        CAPDEVDAO dao = new CAPDEVDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        java.sql.Date implementedDate = null;

        try {
            java.util.Date parsedImplementedDate = sdf.parse(request.getParameter("implementedDate"));
            implementedDate = new java.sql.Date(parsedImplementedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(RecordRepayment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dao.assessCAPDEV(request.getParameter("observation"), request.getParameter("recommendation"));

        

        if (dao.updatePlanStatus(planID, 5, implementedDate)) {
            request.setAttribute("success", "CAPDEV Assessment sent!");
            request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in sending CAPDEV assessment.");
            request.getRequestDispatcher("PP-CAPDEV-view-capdev-plans.jsp").forward(request, response);
        }

    }

}
