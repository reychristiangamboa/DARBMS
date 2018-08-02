/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.AddressDAO;
import com.MVC.Model.APCPRequest;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.Province;
import com.MVC.Model.Region;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class FilterAPCPDashboard extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ARBODAO arboDAO = new ARBODAO();

        AddressDAO addressDAO = new AddressDAO();
        ArrayList<ARBO> arbos = new ArrayList();
        ArrayList<APCPRequest> requests = new ArrayList();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        
        try {
            java.util.Date parsedStartDate = sdf.parse(request.getParameter("start"));
            System.out.println(request.getParameter("start"));
            startDate = new java.sql.Date(parsedStartDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(FilterAPCPDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            java.util.Date parsedEndDate = sdf.parse(request.getParameter("end"));
            System.out.println(request.getParameter("end"));
            endDate = new java.sql.Date(parsedEndDate.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(FilterAPCPDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(startDate.getTime());
        System.out.println(endDate.getTime());

        if ((Integer) session.getAttribute("userType") == 5) {
            arbos = arboDAO.getAllARBOs();
            requests = requestDAO.getAllRequests();
        } else if ((Integer) session.getAttribute("userType") == 4) {
            arbos = arboDAO.getAllARBOsByRegion((Integer) session.getAttribute("regOfficeCode"));
            requests = requestDAO.getAllRegionalRequests((Integer) session.getAttribute("regOfficeCode"));
        }

        ArrayList<ARBO> filtered = new ArrayList();
        ArrayList<APCPRequest> filteredRequests = new ArrayList();

        String filterBy = request.getParameter("filterBy");
        String[] regionsStr = request.getParameterValues("regions[]");
        ArrayList<Integer> regionIDs = new ArrayList();
        String[] provincesStr = request.getParameterValues("provinces[]");
        ArrayList<Integer> provinceIDs = new ArrayList();

        int type = Integer.parseInt(request.getParameter("type"));

        if (filterBy.equals("regions")) {
            if (regionsStr != null) {
                for (String regionStr : regionsStr) {
                    regionIDs.add(Integer.parseInt(regionStr));
                }
            }

            for (int regionID : regionIDs) { // POPULATE FILTERED
                for (ARBO arbo : arbos) {
                    if (arbo.getArboRegion() == regionID) {
                        filtered.add(arbo);
                        arbo.setRequestList(requestDAO.getAllARBORequests(arbo.getArboID()));
                        for (APCPRequest r : arbo.getRequestList()) {
                            filteredRequests.add(r);
                        }
                    }
                }
            }
        } else if (filterBy.equals("provinces")) {
            if (provincesStr != null) {

                for (String provinceStr : provincesStr) {
                    provinceIDs.add(Integer.parseInt(provinceStr));
                }
            }

            for (int provinceID : provinceIDs) { // POPULATE FILTERED
                for (ARBO arbo : arbos) {
                    if (arbo.getProvOfficeCode() == provinceID) {
                        filtered.add(arbo);
                        arbo.setRequestList(requestDAO.getAllARBORequests(arbo.getArboID()));
                        for (APCPRequest r : arbo.getRequestList()) {
                            filteredRequests.add(r);
                        }
                    }
                }
            }
        } else if (filterBy.equals("All")) {
            filtered = arbos;
            filteredRequests = requests;
        }
        
        ArrayList<APCPRequest> filteredRequests2 = filterByDate(filteredRequests,startDate,endDate);

        ArrayList<Region> regions = new ArrayList();
        for (int regionID : regionIDs) {
            Region r = new Region();
            int code = regionID;
            String regDesc = addressDAO.getRegDesc(code);
            r.setRegCode(code);
            r.setRegDesc(regDesc);
            regions.add(r);
        }

        ArrayList<Province> provOffices = new ArrayList();
        for (int provinceID : provinceIDs) {
            Province p = addressDAO.getProvOffice(provinceID);
            provOffices.add(p);
        }

        request.setAttribute("type", type);
        request.setAttribute("filtered", filtered);
        request.setAttribute("filteredRequests", filteredRequests2);
        request.setAttribute("regions", regions);
        request.setAttribute("provOffices", provOffices);
        request.setAttribute("startDate", sdf.format(startDate));
        request.setAttribute("endDate",sdf.format(endDate));

        request.getRequestDispatcher("apcp-dashboard.jsp").forward(request, response);
    }
    
    public ArrayList<APCPRequest> filterByDate(ArrayList<APCPRequest> list, Date start, Date end){
        
        ArrayList<APCPRequest> filtered = new ArrayList();
        
        for(APCPRequest r : list){
            if(isIncluded(r.getDateRequested(), start, end)){
                filtered.add(r);
            }
        }
        
        return filtered;
    }

    public boolean isIncluded(Date d, Date start, Date end){
        return d.after(start) && d.before(end);
    }

}
