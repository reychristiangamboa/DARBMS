/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.Model.APCPDocument;
import com.MVC.Model.APCPRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
public class RequestLoan extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        APCPRequestDAO dao = new APCPRequestDAO();
        APCPRequest r = new APCPRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long l = System.currentTimeMillis();
        Date d = new Date(l);

        String[] vals = request.getParameter("loanAmount").split(",");
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(val);
        }

        String finLoan = sb.toString();
        double loanAmount = Double.parseDouble(finLoan);
        double maxAmount = 0;

        String[] documentIDsStr = request.getParameterValues("documentID"); // document referenceID
        String[] dateSubmittedStr = request.getParameterValues("dateSubmitted");
        String[] documentNameStr = request.getParameterValues("documentName");
        String[] arbIDsStr = request.getParameterValues("arbID");
        //ArrayList<APCPDocument> documentList = new ArrayList();

        r.setRequestStatus(1); // REQUESTED
        for (String str : dateSubmittedStr) {
            if (str.isEmpty()) {
                r.setRequestStatus(8); // INCOMPLETE
            }
        }
        
        if(r.getRequestStatus() == 1){
            r.setDateCompleted(d);
        }
        
        if (request.getParameter("cropProd") != null) {
            APCPRequest cropProdRequest = dao.getRequestByID(Integer.parseInt("cropProd"));
            maxAmount = cropProdRequest.getLoanAmount() * .10;

            //<editor-fold desc="IF-ELSE loanAmount is less than maxAmount">
            if (loanAmount < maxAmount) { // loanAmount doesn't exceed maxAmount (10% of crop prod amount)
                r.setArboID(Integer.parseInt(request.getParameter("arboID")));
                r.setApcpType(Integer.parseInt(request.getParameter("apcpType")));
                r.setCropProdID(Integer.parseInt(request.getParameter("cropProd")));
                r.setLoanAmount(Double.parseDouble(finLoan));
                r.setHectares(Double.parseDouble(request.getParameter("landArea")));
                r.setRemarks(request.getParameter("remarks"));
                r.setLoanTermDuration(Integer.parseInt(request.getParameter("loanTermDuration")));

                int requestID = dao.requestAPCP(r, (Integer) session.getAttribute("userID"));

                // INSERTS into apcp_loanReason table
                dao.setAPCPLoanReason(requestID, Integer.parseInt(request.getParameter("loanReason")), Integer.parseInt(request.getParameter("loanTerm")), request.getParameter("otherReason"));

                for (int i = 0; i < documentIDsStr.length; i++) {
                    System.out.println("DOCUMENT: " + documentIDsStr[i]);
                    APCPDocument doc = new APCPDocument();
                    doc.setDocument(Integer.parseInt(documentIDsStr[i])); // set documentID

                    java.sql.Date dateSubmitted = null;

                    try {
                        if (!dateSubmittedStr[i].isEmpty()) { // dateSubmitted has value
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

                    if (dao.sendDocument(doc, requestID)) {
                        System.out.println("Document added!");
                    }
                }

                System.out.println(request.getParameter("arbListExcel"));

                for (String arbID : arbIDsStr) {
                    System.out.println(arbID);
                    if (dao.addAPCPRecipient(requestID, Integer.parseInt(arbID))) {
                        System.out.println("Recipient added!");
                    }
                }

                request.setAttribute("success", "APCP requested!");
                request.getRequestDispatcher("/PFO-APCP/provincial-field-officer-request-loan-select-arbo").forward(request, response);
            } else {
                request.setAttribute("arboID", Integer.parseInt(request.getParameter("arboID")));
                request.setAttribute("errMessage", "Loan Amount (Php " + loanAmount + ") should not exceed Php " + maxAmount + ".");
            }
            //</editor-fold>
        } else {
            r.setArboID(Integer.parseInt(request.getParameter("arboID")));
            r.setApcpType(Integer.parseInt(request.getParameter("apcpType")));
            r.setCropProdID(Integer.parseInt(request.getParameter("cropProd")));
            r.setLoanAmount(Double.parseDouble(finLoan));
            r.setHectares(Double.parseDouble(request.getParameter("landArea")));
            r.setRemarks(request.getParameter("remarks"));
            r.setLoanTermDuration(Integer.parseInt(request.getParameter("loanTermDuration")));

            int requestID = dao.requestAPCPNewAccess(r, (Integer) session.getAttribute("userID"));

            // INSERTS into apcp_loanReason table
            dao.setAPCPLoanReason(requestID, Integer.parseInt(request.getParameter("loanReason")), Integer.parseInt(request.getParameter("loanTerm")), request.getParameter("otherReason"));

            for (int i = 0; i < documentIDsStr.length; i++) {
                System.out.println("DOCUMENT: " + documentIDsStr[i]);
                APCPDocument doc = new APCPDocument();
                doc.setDocument(Integer.parseInt(documentIDsStr[i])); // set documentID

                java.sql.Date dateSubmitted = null;

                try {
                    if (!dateSubmittedStr[i].isEmpty()) { // dateSubmitted has value
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

                if (dao.sendDocument(doc, requestID)) {
                    System.out.println("Document added!");
                }
            }

            for (String arbID : arbIDsStr) {
                System.out.println(arbID);
                if (dao.addAPCPRecipient(requestID, Integer.parseInt(arbID))) {
                    System.out.println("Recipient added!");
                }
            }

            request.setAttribute("success", "APCP requested!");
            request.getRequestDispatcher("/PFO-APCP/provincial-field-officer-request-loan-select-arbo").forward(request, response);
        }

    }

}
