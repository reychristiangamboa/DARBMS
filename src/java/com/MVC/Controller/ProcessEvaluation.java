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
import com.MVC.Model.Question;
import com.MVC.Model.QuestionRating;
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
public class ProcessEvaluation extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EvaluationDAO eDAO = new EvaluationDAO();
        Evaluation e = eDAO.getEvaluationByID(Integer.parseInt(request.getParameter("evaluationID")));
        
        ARBDAO dao = new ARBDAO();
        ARB arb = dao.getARBByID(e.getArbID());
        
        ArrayList<Question> questions = eDAO.getAllQuestionsByType(e.getEvaluationType());
        ArrayList<Double> ratings = new ArrayList();

        for (Question q : questions) {
            QuestionRating qr = new QuestionRating();
            qr.setEvaluationID(e.getEvaluationID());
            qr.setQuestionID(q.getQuestionID());
            String ratingsStr = request.getParameter(Integer.toString(q.getQuestionID()));
            if(ratingsStr.equals("1")){
                qr.setRating(1);
            }else if(ratingsStr.equals("2")){
                qr.setRating(2);
            }else if(ratingsStr.equals("3")){
                qr.setRating(3);
            }else if(ratingsStr.equals("4")){
                qr.setRating(4);
            }else if(ratingsStr.equals("5")){
                qr.setRating(5);
            }
            
            double finRating = q.getWeight() * qr.getRating();

            eDAO.addQuestionRating(qr);
            ratings.add(finRating);
        }
        
        double rating = calculateAverage(ratings);
        
        if(eDAO.setEvaluationRating(rating, e.getEvaluationID())){
            request.setAttribute("arb", arb);
            request.setAttribute("success", arb.getFLName() + " successfully evaluated!");
            request.getRequestDispatcher("arb-profile.jsp").forward(request, response);
        }else{
            request.setAttribute("arb", arb);
            request.setAttribute("errMessage", "Error in evaluating " + arb.getFLName() + ".");
            request.getRequestDispatcher("arb-profile.jsp").forward(request, response);
        }
    }

    private double calculateAverage(ArrayList<Double> marks) {
        double sum = 0;
        if (!marks.isEmpty()) {
            for (double mark : marks) {
                sum += mark;
            }
            return sum / marks.size();
        }
        return sum;
    }

}
