/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.Model.CityMun;
import com.MVC.Model.Province;
import com.MVC.Model.Region;
import com.MVC.DAO.AddressDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
@WebServlet(name = "RegionRefresh", urlPatterns = {"/RegionRefresh"})
public class RegionRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AddressDAO addressDAO = new AddressDAO();
        ArrayList<Province> provinceList = new ArrayList();

        try (PrintWriter out = response.getWriter()) {
            String valajax = request.getParameter("valajax");
            provinceList = addressDAO.getAllProvinces(Integer.parseInt(valajax));

            response.getWriter().write("<label for='provinceDrop'>Province</label>");
            response.getWriter().write("<select class='form-control' onchange='chg2()' name='arboProvince' id='provinceDrop' style='width: 100%'>");

            response.getWriter().write("<option value='0'> --Select-- </option>");
            for (int j = 0; j < provinceList.size(); j++) {
                response.getWriter().write("<option value=" + provinceList.get(j).getProvCode() + ">" + provinceList.get(j).getProvDesc() + "</option>");
            }

            response.getWriter().write("</select>");

        }
    }

}
