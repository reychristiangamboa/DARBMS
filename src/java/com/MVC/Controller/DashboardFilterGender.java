/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.Chart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class DashboardFilterGender extends BaseServlet {


    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        ARBDAO arbDAO = new ARBDAO();
        ARBODAO arboDAO = new ARBODAO();

        ArrayList<ARBO> arbos = arboDAO.getAllARBOs();
        ArrayList<ARB> filtered = new ArrayList();

        String filterBy = request.getParameter("filterBy");

        String[] regionsStr = request.getParameterValues("regions[]");
        ArrayList<Integer> regionIDs = new ArrayList();
        String[] provincesStr = request.getParameterValues("provinces[]");
        ArrayList<Integer> provinceIDs = new ArrayList();

        if (filterBy.equals("all")) {
            filtered = arbDAO.getAllARBs();
        } else if (filterBy.equals("regions")) {
            if (regionsStr != null) {
                for (String regionStr : regionsStr) {
                    regionIDs.add(Integer.parseInt(regionStr));
                }
            }
            
            for (int regionID : regionIDs) { // POPULATE FILTERED
                for (ARBO arbo : arbos) {
                    if (arbo.getArboRegion() == regionID) {
                        for (ARB arb : arbo.getArbList()) {
                            filtered.add(arb);
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
                        for (ARB arb : arbo.getArbList()) {
                            filtered.add(arb);
                        }
                    }
                }
            }
        }

        request.setAttribute("filteredARBGender", filtered);
        request.getRequestDispatcher("central-home.jsp").forward(request, response);

    }

}
