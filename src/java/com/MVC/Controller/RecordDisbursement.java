/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.Disbursement;
import com.MVC.Model.Repayment;
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
public class RecordDisbursement extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        APCPRequestDAO dao = new APCPRequestDAO();
        Disbursement d = new Disbursement();
        
        d.setReleaseID(Integer.parseInt(request.getParameter("releaseID")));
        d.setArbID(Integer.parseInt(request.getParameter("arbID")));
        d.setAmount(Double.parseDouble(request.getParameter("disbursementAmount")));
        
        java.sql.Date disbursedDate = null;

        try {
            java.util.Date parsedDisbursedDate = sdf.parse(request.getParameter("disbursedDate"));
            disbursedDate = new java.sql.Date(parsedDisbursedDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(RecordDisbursement.class.getName()).log(Level.SEVERE, null, ex);
        }

        d.setDateDisbursed(disbursedDate);
        
        if (dao.addDisbursement(d)) {
            request.setAttribute("success", "Disbursement successfully recorded!");
            request.getRequestDispatcher("").forward(request, response);
        } else {
            request.setAttribute("success", "Error in recording Disbursement.");
            request.getRequestDispatcher("").forward(request, response);
        }

    }

}
