/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.MVC.Model.*;
import com.MVC.DAO.*;
import java.util.ArrayList;

/**
 *
 * @author Rey Christian
 */
public class AddActivityParticipants extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        CAPDEVDAO dao = new CAPDEVDAO();
        ARBDAO arbDAO = new ARBDAO();
        ArrayList<ARB> arbs = new ArrayList();
        int planID = Integer.parseInt(request.getParameter("planID"));
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        
        String[] arbIDsStr = request.getParameterValues("arbID");
        for(String arbID : arbIDsStr){
            ARB arb = arbDAO.getARBByID(Integer.parseInt(arbID));
            arbs.add(arb);
        }
        
        if(dao.addCAPDEVPlanActivityParticipants(arbs, activityID)){
            request.setAttribute("planID", planID);
            request.setAttribute("participants", true);
            request.getRequestDispatcher("PFO-CAPDEV-create-capdev-proposal.jsp").forward(request, response);
        }
        
        
        
    }

    

}
