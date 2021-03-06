/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ijJPN
 */
public class AddCAPDEVParticipants extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ARBODAO arboDAO = new ARBODAO();
        ARBDAO arbDAO = new ARBDAO();

        boolean missing = false;

        String[] activities = request.getParameterValues("activities[]");
        String[] activityDates = request.getParameterValues("activityDates[]");
        String[] files = request.getParameterValues("file[]");

        if (files.length != activities.length) {
            request.setAttribute("errMessage", "Activity length does not match Participants length!");
            request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
            request.getRequestDispatcher("provincial-field-officer-create-capdev-proposal.jsp").forward(request, response);
        } else {
            ARBO arbo = arboDAO.getARBOByID(Integer.parseInt(request.getParameter("requestID")));
            ArrayList<ARB> allARBs = arbDAO.getAllARBsARBO(arbo.getArboID());

//            for (int i = 0; i < files.length; i++) {
//
//                ArrayList arbHolder = readExcelFile(files[i]);
//                ArrayList cellStoreArrayList = new ArrayList();
//                ArrayList<ARB> arbList = new ArrayList();
//
//                for (int a = 1; a < arbHolder.size(); a++) {
//
//                    cellStoreArrayList = (ArrayList) arbHolder.get(a);
//                    String lN = cellStoreArrayList.get(0).toString();
//                    String fN = cellStoreArrayList.get(1).toString();
//                    String mN = cellStoreArrayList.get(2).toString();
//                    boolean isPart = false;
//
//                    int arbID = arbDAO.getARBID(fN, mN, lN);
//
//                    for (ARB arb : allARBs) {
//                        if (arb.getArbID() == arbID) {
//                            isPart = true;
//                        }
//                    }
//
//                    if (arbID > 0 && isPart) {
//                        System.out.println(arbID);
//                        ARB arb = arbDAO.getARBByID(arbID);
//                        arbList.add(arb);
//                    }
//
//                    if (arbList.isEmpty()) {
//                        missing = true;
//                    }
//                }
//            }
//            if (missing) {
//                request.setAttribute("errMessage", "There are no valid participants in the Excel.");
//                request.setAttribute("requestID", Integer.parseInt(request.getParameter("requestID")));
//                request.getRequestDispatcher("provincial-field-officer-create-capdev-proposal.jsp").forward(request, response);
//            } else {
            CAPDEVPlan capdevPlan = new CAPDEVPlan();

            capdevPlan.setRequestID(Integer.parseInt(request.getParameter("requestID")));
            capdevPlan.setPlanDTN(request.getParameter("dtn"));

            int planID = 0;

            if (request.getParameter("pastDueID") != null) {
                capdevPlan.setPastDueAccountID(Integer.parseInt(request.getParameter("pastDueID")));
                planID = capdevDAO.addCAPDEVPlanForPastDue(capdevPlan, (Integer) session.getAttribute("userID"));
            } else {
                planID = capdevDAO.addCAPDEVPlan(capdevPlan, (Integer) session.getAttribute("userID"));
            }

            for (int i = 0; i < activities.length; i++) {

                CAPDEVActivity activity = new CAPDEVActivity();
                ArrayList<ARB> arbList = new ArrayList();
                ArrayList arbHolder = readExcelFile(files[i]);

                ArrayList cellStoreArrayList = new ArrayList();

                for (int a = 1; a < arbHolder.size(); a++) {

                    cellStoreArrayList = (ArrayList) arbHolder.get(a);
                    String lN = cellStoreArrayList.get(0).toString();
                    String fN = cellStoreArrayList.get(1).toString();
                    String mN = cellStoreArrayList.get(2).toString();
                    boolean isPart = false;

                    int arbID = arbDAO.getARBID(fN, mN, lN);

                    ARB arb = arbDAO.getARBByID(arbID);
                    arbList.add(arb);

                }

                java.sql.Date activityDate = null;

                try {
                    java.util.Date parsedActivityDate = sdf.parse(activityDates[i]);
                    activityDate = new java.sql.Date(parsedActivityDate.getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(SendCAPDEVProposal.class.getName()).log(Level.SEVERE, null, ex);
                }

                int activityType = Integer.parseInt(activities[i]);
                activity.setActivityType(activityType);
                activity.setPlanID(planID);

                int newlyAddedActivityID = capdevDAO.addCAPDEVPlanActivity(activity);

                capdevDAO.addCAPDEVPlanActivityParticipants(arbList, newlyAddedActivityID);

            }

            request.setAttribute("success", "CAPDEV plan submitted!");
            request.getRequestDispatcher("view-capdev-status.jsp").forward(request, response);
//            }

        }

    }

    public static ArrayList readExcelFile(String fileName) {
        ArrayList cellArrayListHolder = new ArrayList();
        try {

            OPCPackage pkg = OPCPackage.open(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator rowIter = sheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow row = (XSSFRow) rowIter.next();
                Iterator cellIter = row.cellIterator();

                ArrayList cellStoreArrayList = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIter.next();
                    cellStoreArrayList.add(cell);
                }
                cellArrayListHolder.add(cellStoreArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellArrayListHolder;
    }

}
