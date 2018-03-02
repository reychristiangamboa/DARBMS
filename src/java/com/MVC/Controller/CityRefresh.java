/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
import com.MVC.Model.Barangay;
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
public class CityRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<Barangay> barangayList = new ArrayList();
        
        System.out.println(request.getParameter("valajax"));

        try (PrintWriter out = response.getWriter()) {
            String valajax = request.getParameter("valajax");
            barangayList = addressDAO.getAllBarangays(Integer.parseInt(valajax));

            response.getWriter().write("<label for='barangayDrop'>Barangay</label>");
            response.getWriter().write("<select class='form-control' name='barangay' id='barangayDrop' style='width: 100%'>");

            for (int j = 0; j < barangayList.size(); j++) {
                response.getWriter().write("<option value=" + barangayList.get(j).getBrgyCode() + ">" + barangayList.get(j).getBrgyDesc() + "</option>");
            }
            
            response.getWriter().write("</select");

        }
    }

    

}
