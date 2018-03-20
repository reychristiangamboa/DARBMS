/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.CAPDEVActivity;
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
public class EditCAPDEVActivity extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CAPDEVDAO dao = new CAPDEVDAO();
        CAPDEVActivity a = new CAPDEVActivity();
        a.setActivityID(Integer.parseInt(request.getParameter("activityID")));
        a.setActivityName(request.getParameter("activityName"));
        a.setActivityDesc(request.getParameter("activityDesc"));
        a.setActivityCategory(Integer.parseInt(request.getParameter("category")));
        
        if(dao.editCAPDEVActivity(a)){
            request.setAttribute("success", "CAPDEV activity updated!");
            request.getRequestDispatcher("central-manage-capdev-activities.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in updating CAPDEV activity.");
            request.getRequestDispatcher("central-manage-capdev-activities.jsp").forward(request, response);
        }
        
    }

    

}