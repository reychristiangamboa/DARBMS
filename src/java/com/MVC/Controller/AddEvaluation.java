/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.EvaluationDAO;
import com.MVC.Model.ARB;
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
        java.sql.Date maxDate = null;

        System.out.println(request.getParameter("evaluationDate"));

        try {
            java.util.Date parsedEvaluationDate = sdf.parse(request.getParameter("evaluationDate"));
            evaluationDate = new java.sql.Date(parsedEvaluationDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AddEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        e.setEvaluationDate(evaluationDate);

        try {
            java.util.Date parsedStart = sdf.parse(request.getParameter("start"));
            startDate = new java.sql.Date(parsedStart.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AddEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        e.setEvaluationStartDate(startDate);

        try {
            java.util.Date parsedEnd = sdf.parse(request.getParameter("end"));
            endDate = new java.sql.Date(parsedEnd.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AddEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        e.setEvaluationEndDate(endDate);

        try {
            java.util.Date parsedMaxDate = sdf.parse(request.getParameter("maxDate"));
            maxDate = new java.sql.Date(parsedMaxDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(AddEvaluation.class.getName()).log(Level.SEVERE, null, ex);
        }

        e.setArbID(Integer.parseInt(request.getParameter("id")));
        e.setEvaluationDTN(request.getParameter("dtn"));
        e.setEvaluationType(Integer.parseInt(request.getParameter("type")));
        e.setEvaluatedBy((Integer) session.getAttribute("userID"));

        if (e.getEvaluationDate().after(maxDate)) {
            ARBDAO arbdao = new ARBDAO();
            ARB arb = arbdao.getARBByID(e.getArbID());
            request.setAttribute("arb", arb);
            request.setAttribute("errMessage", "Evaluation Date cannot go beyond one quarter of selected QUARTER.");
            request.getRequestDispatcher("arb-profile.jsp").forward(request, response);
        } else {
            int evaluationID = dao.addEvaluation(e);
            request.setAttribute("evaluationID", evaluationID);
            request.getRequestDispatcher("point-person-evaluation-form.jsp").forward(request, response);
        }

    }

}
