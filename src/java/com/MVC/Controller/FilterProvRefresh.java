/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
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
 * @author Christopher Jorge
 */
public class FilterProvRefresh extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<Province> provinceList = new ArrayList();

        try (PrintWriter out = response.getWriter()) {
            String valajax = request.getParameter("valajax");
            provinceList = addressDAO.getAllProvOfficesRegion(Integer.parseInt(valajax));

            for (int j = 0; j < provinceList.size(); j++) {
                response.getWriter().write("<option value=" + provinceList.get(j).getProvCode() + ">" + provinceList.get(j).getProvDesc() + "</option>");
            }
        
        }
    }

    

}
