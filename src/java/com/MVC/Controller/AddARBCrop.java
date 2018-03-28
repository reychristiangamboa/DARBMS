/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.CropDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.Crop;
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

/**
 *
 * @author Rey Christian
 */
public class AddARBCrop extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String[] cropIDs = request.getParameterValues("cropType");
        String[] startDates = request.getParameterValues("startDate");
        String[] endDates = request.getParameterValues("endDate");
        int arbID = Integer.parseInt(request.getParameter("arbID"));
        ARBDAO dao = new ARBDAO();
        ARB arb = dao.getARBByID(arbID);
        
        ArrayList<Crop> cropList = new ArrayList();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        
        for(int i = 0; i < cropIDs.length; i++){
            Crop c = new Crop();
            c.setArbID(arbID);
            c.setCropType(Integer.parseInt(cropIDs[i]));
            
            java.sql.Date startDate = null;

            try {
                java.util.Date parsedStartDate = sdf.parse(startDates[i]);
                startDate = new java.sql.Date(parsedStartDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(AddARBCrop.class.getName()).log(Level.SEVERE, null, ex);
            }

            c.setStartDate(startDate);
            
            java.sql.Date endDate = null;

            try {
                java.util.Date parsedEndDate = sdf.parse(endDates[i]);
                endDate = new java.sql.Date(parsedEndDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(AddARBCrop.class.getName()).log(Level.SEVERE, null, ex);
            }

            c.setEndDate(endDate);
            
            cropList.add(c);
            
        }
        
        if(dao.addCrops(arbID, cropList)){
            request.setAttribute("success", "Crops successfully added!");
            request.setAttribute("arb", arb);
            request.getRequestDispatcher("arb-profile.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in adding crops. Try again.");
            request.setAttribute("arb", arb);
            request.getRequestDispatcher("arb-profile.jsp").forward(request, response);
        }
        
    }

    

}
