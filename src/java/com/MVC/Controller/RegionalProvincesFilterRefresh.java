/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
import com.MVC.Model.CityMun;
import com.MVC.Model.Province;
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
public class RegionalProvincesFilterRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<Integer> regionIDs = new ArrayList();
        ArrayList<Province> provinces = new ArrayList();

        try (PrintWriter out = response.getWriter()) {
            
            String[] valajax = request.getParameterValues("valajax");
            for (String val : valajax) {
                regionIDs.add(Integer.parseInt(val));
                System.out.println(val);
            }
            
            provinces = addressDAO.getAllProvOfficesMultipleReg(regionIDs);
            System.out.println(provinces.size());
            
            for (int j = 0; j < provinces.size(); j++) {
                response.getWriter().write("<option value='" + provinces.get(j).getProvCode()+ "'>" + provinces.get(j).getProvDesc()+ "</option>");
            }

        }
    }

    

}
