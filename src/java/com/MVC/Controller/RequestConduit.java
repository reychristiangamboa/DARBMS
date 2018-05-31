/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.MVC.Model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class RequestConduit extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRequest r = new APCPRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        r.setArboID(Integer.parseInt(request.getParameter("arboID")));
        r.setApcpType(Integer.parseInt(request.getParameter("apcpType")));
        r.setLoanReason(Integer.parseInt(request.getParameter("loanReason")));
        r.setOtherLoanReason(request.getParameter("otherReason"));
        r.setLoanAmount(Double.parseDouble(request.getParameter("loanAmount")));
        r.setHectares(Double.parseDouble(request.getParameter("landArea")));
        r.setRemarks(request.getParameter("remarks"));

        int requestID = dao.requestAPCP(r, (Integer) session.getAttribute("userID"));

        String[] documentIDsStr = request.getParameterValues("documentID"); // document referenceID
        System.out.println(documentIDsStr.length);
        String[] dateSubmittedStr = request.getParameterValues("dateSubmitted");
        String[] documentNameStr = request.getParameterValues("documentName");
        String[] arbIDsStr = request.getParameterValues("arbID");
        //ArrayList<APCPDocument> documentList = new ArrayList();

        r.setRequestStatus(0); // FOR CONDUIT APPROVAL
        for (String str : dateSubmittedStr) {
            if (str.isEmpty()) {
                r.setRequestStatus(8); // INCOMPLETE
            }
        }

        for (int i = 0; i < documentIDsStr.length; i++) {
            System.out.println("DOCUMENT: "+documentIDsStr[i]);
            APCPDocument doc = new APCPDocument();
            doc.setDocument(Integer.parseInt(documentIDsStr[i])); // set documentID

            java.sql.Date dateSubmitted = null;

            try {
                if (!dateSubmittedStr[i].isEmpty()) {
                    java.util.Date parsedDateSubmitted = sdf.parse(dateSubmittedStr[i]);
                    dateSubmitted = new java.sql.Date(parsedDateSubmitted.getTime());
                    doc.setDateSubmitted(dateSubmitted); // set dateSubmitted
                }
            } catch (ParseException ex) {
                Logger.getLogger(RequestConduit.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (documentIDsStr[i].equals("12")) { // Others
                doc.setDocumentName(documentNameStr[i]); // set documentName 
            }

            if(dao.sendDocument(doc, requestID)){
                System.out.println("LOL");
            }
        }
        
        if(request.getParameter("arbListExcel") != null){ // EXCEL UPLOADED
            // EXCEL
        }else{ // NOTHING WAS UPLOADED
            for(String arbID : arbIDsStr){
                dao.addAPCPRecipient(requestID, Integer.parseInt(arbID));
            }
        }

    }

}
