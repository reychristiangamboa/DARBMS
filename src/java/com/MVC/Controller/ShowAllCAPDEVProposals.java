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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class ShowAllCAPDEVProposals extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AddressDAO addressDAO = new AddressDAO();
        ArrayList<Province> provinceList = new ArrayList();

        try (PrintWriter out = response.getWriter()) {
            String valajax = request.getParameter("valajax");
            provinceList = addressDAO.getAllProvOfficesRegion((Integer)session.getAttribute("regOfficeCode"));

            for (int j = 0; j < provinceList.size(); j++) {
                response.getWriter().write("<option value=" + provinceList.get(j).getProvCode() + ">" + provinceList.get(j).getProvDesc() + "</option>");
            }
        
        }
    }

}
