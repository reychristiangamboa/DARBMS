/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBODAO;
import com.MVC.Model.ARBO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rey Christian
 */
public class AddARBO extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("manual") != null) {

            HttpSession session = request.getSession();

            ARBODAO arboDAO = new ARBODAO();
            ARBO arbo = new ARBO();

            /*String id = String.valueOf(Integer.parseInt(request.getParameter("arboProvince")));
            int size = arboDAO.getAllARBOsByProvince(Integer.parseInt(request.getParameter("arboProvince"))).size();
            int counter = size + 1;

            if (size <= 9) {
                id += "00" + counter;
            } else if (size >= 10) {
                id += "0" + counter;
            } else if (size >= 100) {
                id += counter;
            }

            int finalID = Integer.parseInt(id);*/

            arbo.setArboID(Integer.parseInt(request.getParameter("arboID")));
            arbo.setArboName(request.getParameter("arboName"));
            arbo.setArboType(Integer.parseInt(request.getParameter("arboType")));
            arbo.setArboCityMun(Integer.parseInt(request.getParameter("arboCityMun")));
            arbo.setArboProvince(Integer.parseInt(request.getParameter("arboProvince")));
            arbo.setArboRegion(Integer.parseInt(request.getParameter("arboRegion")));
            arbo.setProvOfficeCode((Integer) session.getAttribute("provOfficeCode"));

            session.setAttribute("arbo", arbo);
            request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);

        }
    }
}
