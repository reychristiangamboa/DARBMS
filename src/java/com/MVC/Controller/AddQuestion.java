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
public class AddQuestion extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int type = Integer.parseInt(request.getParameter("type"));

        EvaluationDAO dao = new EvaluationDAO();
        Question q = new Question();
        q.setQuestion(request.getParameter("question"));
        q.setWeight(Double.parseDouble(request.getParameter("weight")));
        q.setQuestionType(type);

        ArrayList<Question> questions = dao.getAllQuestionsByType(Integer.parseInt(request.getParameter("type")));

        if (dao.addQuestion(q)) {
            request.setAttribute("success", "Successfully added question!");
            request.setAttribute("questions", questions);
            request.setAttribute("type", Integer.parseInt(request.getParameter("type")));
            request.getRequestDispatcher("central-manage-evaluations.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in adding question!");
            request.setAttribute("questions", questions);
            request.setAttribute("type", Integer.parseInt(request.getParameter("type")));
            request.getRequestDispatcher("central-manage-evaluations.jsp").forward(request, response);
        }

    }

}
