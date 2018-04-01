/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
import com.MVC.Model.CityMun;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class ProvincialCitiesFilterRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<Integer> provinceIDs = new ArrayList();
        ArrayList<CityMun> cityMuns = new ArrayList();

        try (PrintWriter out = response.getWriter()) {
            
            String[] valajax = request.getParameterValues("valajax");
            for (String val : valajax) {
                provinceIDs.add(Integer.parseInt(val));
                System.out.println(val);
            }
            
            cityMuns = addressDAO.getAllCityMunsMultipleProv(provinceIDs);
            System.out.println(cityMuns.size());
            
            for (int j = 0; j < cityMuns.size(); j++) {
                response.getWriter().write("<option value='" + cityMuns.get(j).getCityMunCode()+ "'>" + cityMuns.get(j).getCityMunDesc()+ "</option>");
            }

        }
        
    }

    

}
