/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import static com.MVC.Controller.SendCAPDEVProposal.readExcelFile;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.ARB;
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
public class SendSchedulePreRelease extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HttpSession session = request.getSession();

        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ARBDAO arbDAO = new ARBDAO();

        ArrayList<ARB> arbList = new ArrayList();
        ArrayList arbHolder = readExcelFile(request.getParameter("file"));
        ArrayList cellStoreArrayList = new ArrayList();

        CAPDEVPlan capdevPlan = new CAPDEVPlan();
        capdevPlan.setRequestID(Integer.parseInt(request.getParameter("requestID")));
        capdevPlan.setAssignedTo(Integer.parseInt(request.getParameter("pointPerson")));
        capdevPlan.setCreatedBy((Integer) session.getAttribute("userID"));
        capdevPlan.setPlanDTN(request.getParameter("dtn"));
        capdevPlan.setPlanStatus(6);

        int planID = capdevDAO.addPreReleaseOrientation(capdevPlan);

        CAPDEVActivity activity = new CAPDEVActivity();
        java.sql.Date activityDate = null;

        try {
            java.util.Date parsedActivityDate = sdf.parse(request.getParameter("activityDate"));
            activityDate = new java.sql.Date(parsedActivityDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(SendSchedulePreRelease.class.getName()).log(Level.SEVERE, null, ex);
        }

        int activityType = Integer.parseInt(request.getParameter("activityType"));

        activity.setActivityType(activityType);
        activity.setPlanID(planID);
        activity.setActivityDate(activityDate);

        int newlyAddedActivityID = capdevDAO.addCAPDEVPlanActivity(activity);

        for (int a = 1; a < arbHolder.size(); a++) {

            cellStoreArrayList = (ArrayList) arbHolder.get(a);
            String lN = cellStoreArrayList.get(0).toString();
            String fN = cellStoreArrayList.get(1).toString();
            String mN = cellStoreArrayList.get(2).toString();
            
            int arbID = arbDAO.getARBID(fN, mN, lN);
            ARB arb = arbDAO.getARBByID(arbID);

            arbList.add(arb);
        }

        if (capdevDAO.addCAPDEVPlanActivityParticipants(arbList, newlyAddedActivityID)) {
            request.setAttribute("success", "Pre-release orientation scheduled!");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
        } else {
            request.setAttribute("errMessage", "Error in scheduling pre-release orientation.");
            request.getRequestDispatcher("view-apcp-status.jsp").forward(request, response);
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
