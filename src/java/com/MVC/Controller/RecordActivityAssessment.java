/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import static com.MVC.Controller.SendCAPDEVProposal.readExcelFile;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.CAPDEVDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.CAPDEVActivity;
import com.MVC.Model.CAPDEVPlan;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ijJPN
 */
public class RecordActivityAssessment extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CAPDEVActivity ca = new CAPDEVActivity();
        ArrayList<Integer> arbIDs = new ArrayList();
        CAPDEVDAO cDAO = new CAPDEVDAO();
        ARBDAO arbDAO = new ARBDAO();
        APCPRequestDAO rDAO = new APCPRequestDAO();
        ARBODAO arboDAO = new ARBODAO();

        ArrayList arbHolder = readExcelFile(request.getParameter("file"));
        ArrayList cellStoreArrayList = new ArrayList();

        int activityID = Integer.parseInt(request.getParameter("activityID"));
        int planID = Integer.parseInt(request.getParameter("planID"));

        CAPDEVPlan plan = cDAO.getCAPDEVPlan(planID);
        APCPRequest req = rDAO.getRequestByID(plan.getRequestID());
        ARBO arbo = arboDAO.getARBOByID(req.getArboID());
        ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(arbo.getArboID());

        ca.setActivityID(activityID);
        ca.setActive(1);
        ca.setTechnicalAssistant(request.getParameter("TA"));
        ca.setObservations(request.getParameter("observations"));
        ca.setRecommendation(request.getParameter("recommendation"));

        for (int a = 1; a < arbHolder.size(); a++) {

            cellStoreArrayList = (ArrayList) arbHolder.get(a);
            String lN = cellStoreArrayList.get(0).toString();
            String fN = cellStoreArrayList.get(1).toString();
            String mN = cellStoreArrayList.get(2).toString();

            boolean isPart = false;
            
            int arbID = arbDAO.getARBID(fN, mN, lN);
            
            for(ARB arb : arbList){
                if(arb.getArbID() == arbID){
                    isPart = true;
                }
            }

            if (arbID > 0 && isPart) {
                arbIDs.add(arbID);
            }

        }

        if (cDAO.recordAssessment(ca) && cDAO.checkIfPresent(arbIDs, activityID)) {
            request.setAttribute("success", "Activity Assessment recorded!");
            request.setAttribute("planID", planID);
            request.getRequestDispatcher("point-person-review-capdev-attendance.jsp").forward(request, response);

        } else {
            request.setAttribute("errMessage", "Unable to record Activity Assessment.");
            request.setAttribute("planID", planID);
            request.getRequestDispatcher("point-person-review-capdev-attendance.jsp").forward(request, response);
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
