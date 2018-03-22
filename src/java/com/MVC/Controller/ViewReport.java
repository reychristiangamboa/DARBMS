/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.Model.Evaluation;
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
public class ViewReport extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HttpSession session = request.getSession();
        Evaluation e = new Evaluation();
        
        java.sql.Date startDate = null;

        try {
            java.util.Date parsedStartDate = sdf.parse(request.getParameter("start"));
            startDate = new java.sql.Date(parsedStartDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ViewReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        e.setEvaluationStartDate(startDate);
        
        java.sql.Date endDate = null;

        try {
            java.util.Date parsedEndDate = sdf.parse(request.getParameter("end"));
            endDate = new java.sql.Date(parsedEndDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(ViewReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        e.setEvaluationEndDate(endDate);
        
        if(Integer.parseInt(request.getParameter("reportType")) == 1){
            request.setAttribute("evaluation", e);
            request.getRequestDispatcher("report-yearly-release.jsp").forward(request, response);
        }else if(Integer.parseInt(request.getParameter("reportType")) == 2){
            request.setAttribute("evaluation", e);
            request.getRequestDispatcher("report-accumulated-release.jsp").forward(request, response);
        }

    }

}
