/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.EvaluationDAO;
import com.MVC.Model.Evaluation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class AddEvaluation extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        HttpSession session = request.getSession();
        
        EvaluationDAO dao = new EvaluationDAO();
        Evaluation e = new Evaluation();
        
        java.sql.Date evaluationDate = null;
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;

            try {
                java.util.Date parsedEvaluationDate = sdf.parse(request.getParameter("evaluationDate"));
                java.util.Date parsedStart = sdf.parse(request.getParameter("startDate"));
                java.util.Date parsedEnd = sdf.parse(request.getParameter("endDate"));
                evaluationDate = new java.sql.Date(parsedEvaluationDate.getTime());
                startDate = new java.sql.Date(parsedStart.getTime());
                endDate = new java.sql.Date(parsedEnd.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(AddEvaluation.class.getName()).log(Level.SEVERE, null, ex);
            }

            e.setArbID(Integer.parseInt(request.getParameter("id")));
            e.setEvaluationDate(evaluationDate);
            e.setEvaluationStartDate(startDate);
            e.setEvaluationEndDate(endDate);
            e.setEvaluationDTN(request.getParameter("dtn"));
            e.setEvaluationType(Integer.parseInt(request.getParameter("type")));
            e.setEvaluatedBy((Integer)session.getAttribute("userID"));
            
            int evaluationID = dao.addEvaluation(e);
            
            request.setAttribute("evaluationID", evaluationID);
            request.getRequestDispatcher("point-person-evaluation-form.jsp").forward(request, response);
        
    }

    

}
