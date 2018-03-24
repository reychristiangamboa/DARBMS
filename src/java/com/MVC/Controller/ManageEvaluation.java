/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.EvaluationDAO;
import com.MVC.Model.Question;
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
public class ManageEvaluation extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        EvaluationDAO eDAO = new EvaluationDAO();
        ArrayList<Question> questions = eDAO.getAllQuestionsByType(Integer.parseInt(request.getParameter("type")));
        request.setAttribute("questions",questions);
        request.setAttribute("type", Integer.parseInt(request.getParameter("type")));
        request.getRequestDispatcher("central-manage-evaluations.jsp").forward(request, response);
        
    }

    

}
