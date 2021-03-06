/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.AddressDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class FilterARBDashboard extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ARBDAO arbDAO = new ARBDAO();
        ARBODAO arboDAO = new ARBODAO();
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<ARBO> arbos = new ArrayList();

        if ((Integer) session.getAttribute("userType") == 5) {
            arbos = arboDAO.getAllARBOs();
        } else if ((Integer) session.getAttribute("userType") == 4) {
            arbos = arboDAO.getAllARBOsByRegion((Integer) session.getAttribute("regOfficeCode"));
        } else if ((Integer) session.getAttribute("userType") == 3) {
            arbos = arboDAO.getAllARBOsByProvince((Integer) session.getAttribute("provOfficeCode"));
        }

        ArrayList<ARB> filtered = new ArrayList();

        String filterBy = request.getParameter("filterBy");
        String[] regionsStr = request.getParameterValues("regions[]");
        ArrayList<Integer> regionIDs = new ArrayList();
        String[] provincesStr = request.getParameterValues("provinces[]");
        ArrayList<Integer> provinceIDs = new ArrayList();

        int demographic = Integer.parseInt(request.getParameter("demographic"));
        String demographicDesc = "";
        int category = Integer.parseInt(request.getParameter("category"));
        String categoryDesc = "";

        switch (demographic) {
            case 1:
                demographicDesc = "Gender";
                break;
            case 2:
                demographicDesc = "Age";
                break;
            default:
                demographicDesc = "N/A";
                break;
        }

        switch (category) {
            case 1:
                categoryDesc = "COUNT";
                break;
            case 2:
                categoryDesc = "APCP Recipients";
                break;
            case 3:
                categoryDesc = "APCP/CAPDEV Participants";
                break;
            case 4:
                categoryDesc = "Participation Rate";
                break;
            case 5:
                categoryDesc = "Mean Ave. Disbursed Amount";
                break;
            case 6:
                categoryDesc = "Mean Ave. O/S Balance Amount";
                break;
            default:
                categoryDesc = "N/A";
                break;
        }

        if (filterBy.equals("All")) {
            if ((Integer) session.getAttribute("userType") == 5) { // CENTRAL
                if (category == 2) { // RECIPIENTS
                    filtered = arbDAO.getAllAPCPRecipients();
                } else {
                    filtered = arbDAO.getAllARBs();
                }
            } else if ((Integer) session.getAttribute("userType") == 4) { // RFO
                filtered = arbDAO.getAllRegionalARBs((Integer) session.getAttribute("regOfficeCode"));
            } else if ((Integer) session.getAttribute("userType") == 3) { // PFO
                filtered = arbDAO.getAllProvincialARBs((Integer) session.getAttribute("provOfficeCode"));
            }
        } else if (filterBy.equals("regions")) {
            request.setAttribute("filterByREGION", true);
            if (regionsStr != null) {
                for (String regionStr : regionsStr) {
                    regionIDs.add(Integer.parseInt(regionStr));
                }
            }

            if (category == 2) {
                for (int regionID : regionIDs) {
                    for (ARB arb : arbDAO.getAllAPCPRecipients()) {
                        if (arb.getRegCode() == regionID) {
                            filtered.add(arb);
                        }
                    }
                }
            } else {

                for (int regionID : regionIDs) { // POPULATE FILTERED
                    for (ARBO arbo : arbos) {
                        if (arbo.getArboRegion() == regionID) {
                            arbo.setArbList(arbDAO.getAllARBsARBO(arbo.getArboID()));
                            for (ARB arb : arbo.getArbList()) {
                                filtered.add(arb);
                            }
                        }
                    }
                }
            }
        } else if (filterBy.equals("provinces")) {
            request.setAttribute("filterByPROVINCE", true);
            if (provincesStr != null) {

                for (String provinceStr : provincesStr) {
                    provinceIDs.add(Integer.parseInt(provinceStr));
                }
            }

            for (int provinceID : provinceIDs) { // POPULATE FILTERED
                for (ARBO arbo : arbos) {
                    if (arbo.getProvOfficeCode() == provinceID) {
                        arbo.setArbList(arbDAO.getAllARBsARBO(arbo.getArboID()));
                        for (ARB arb : arbo.getArbList()) {
                            filtered.add(arb);
                        }
                    }
                }
            }
        }

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

        request.setAttribute("category", category);
        request.setAttribute("categoryDesc", categoryDesc);
        request.setAttribute("demographic", demographic);
        request.setAttribute("demographicDesc", demographicDesc);
        request.setAttribute("filtered", filtered);
        request.setAttribute("regions", regions);
        request.setAttribute("provOffices", provOffices);

        request.getRequestDispatcher("arb-dashboard.jsp").forward(request, response);

    }
}
