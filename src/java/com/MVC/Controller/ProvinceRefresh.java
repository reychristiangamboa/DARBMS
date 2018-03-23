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
public class ProvinceRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<CityMun> cityMunList = new ArrayList();
        
        try(PrintWriter out = response.getWriter()){
            String valajax = request.getParameter("valajax");
            cityMunList = addressDAO.getAllCityMuns(Integer.parseInt(valajax));
            
            response.getWriter().write("<label for=''>City</label>");
            response.getWriter().write("<select class='form-control' name='arboCityMun' onchange='chg3()' id='cityDrop' style='width:100%'>");
            
            response.getWriter().write("<option value=''>--Select--</option>");
            for(int i = 0; i < cityMunList.size(); i++){
                response.getWriter().write("<option value=" + cityMunList.get(i).getCityMunCode() + ">" + cityMunList.get(i).getCityMunDesc() + "</option>");
            }
            
            response.getWriter().write("</select>");
        }
    }

    
}
