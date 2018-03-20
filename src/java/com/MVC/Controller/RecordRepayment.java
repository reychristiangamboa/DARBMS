/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPRelease;
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
public class RecordRepayment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] arbIDs = request.getParameterValues("arbIDs");
        System.out.println(arbIDs.length);
        APCPRequestDAO dao = new APCPRequestDAO();

        for (String arbID : arbIDs) {
            Repayment r = new Repayment();

            r.setArbID(Integer.parseInt(arbID));
            r.setRequestID(Integer.parseInt(request.getParameter("requestID")));

            String[] vals = request.getParameter("repaymentAmount").split(",");
            StringBuilder sb = new StringBuilder();
            for (String val : vals) {
                sb.append(val);
            }

            String finAmount = sb.toString();

            r.setAmount(Double.parseDouble(finAmount));

            java.sql.Date repaymentDate = null;

            try {
                java.util.Date parsedRepaymentDate = sdf.parse(request.getParameter("repaymentDate"));
                repaymentDate = new java.sql.Date(parsedRepaymentDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(RecordRepayment.class.getName()).log(Level.SEVERE, null, ex);
            }

            r.setDateRepayment(repaymentDate);
            r.setRecordedBy((Integer) session.getAttribute("userID"));

            dao.addRepayment(r);
        }

        request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
        request.setAttribute("success", "Repayment successfully recorded!");
        request.getRequestDispatcher("point-person-monitor-release.jsp").forward(request, response);

    }

}
