/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.PastDueAccount;
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

/**
 *
 * @author Rey Christian
 */
public class SettlePastDueAccount extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        APCPRequestDAO dao = new APCPRequestDAO();
        PastDueAccount pda = new PastDueAccount();
        PastDueAccount oldPDA = new PastDueAccount();
        PastDueAccount newPDA = new PastDueAccount();
        oldPDA = dao.getPastDueAccountByID(Integer.parseInt(request.getParameter("pastDueID")));
        double difference = 0;

        java.sql.Date dateSettled = null;

        try {
            java.util.Date parsedDateSettled = sdf.parse(request.getParameter("dateSettled"));
            dateSettled = new java.sql.Date(parsedDateSettled.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(SettlePastDueAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        pda.setPastDueAccountID(Integer.parseInt(request.getParameter("pastDueID")));
        pda.setDateSettled(dateSettled);

        System.out.println(request.getParameter("amount"));
        System.out.println(pda.getPastDueAccountID());

        if (request.getParameter("paymentMode").equals("full")) {
            pda.setPastDueAmount(oldPDA.getPastDueAmount());
        } else {
            String[] vals = request.getParameter("amount").split(",");
            StringBuilder sb = new StringBuilder();
            for (String val : vals) {
                sb.append(val);
            }

            String finAmount = sb.toString();

            pda.setPastDueAmount(Double.parseDouble(finAmount));
        }

        if (dao.settlePastDueAccount(pda)) {
            if (request.getParameter("paymentMode").equals("partial")) {
                
                difference = oldPDA.getPastDueAmount() - pda.getPastDueAmount();
                
                newPDA.setPastDueAmount(difference);
                newPDA.setRequestID(oldPDA.getRequestID());
                newPDA.setReasonPastDue(oldPDA.getReasonPastDue());
                newPDA.setOtherReason(oldPDA.getOtherReason());
                newPDA.setRecordedBy(oldPDA.getRecordedBy());
                newPDA.setDateRecorded(oldPDA.getDateRecorded());
                newPDA.setActive(oldPDA.getActive());
                
                dao.addPastDueAccount(newPDA);
                    
            }
            request.setAttribute("success", "Past Due Account successfully settled!");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("id")));
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in settling Past Due Account.");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("id")));
            request.getRequestDispatcher("monitor-release.jsp").forward(request, response);
        }
    }

}
