/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.AddressDAO;
import com.MVC.DAO.LINKSFARMDAO;
import com.MVC.Model.CityMun;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class ApproveProjectSite extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        LINKSFARMDAO linksfarmDAO = new LINKSFARMDAO();
        AddressDAO dao = new AddressDAO();
        CityMun c = dao.getCityMun(Integer.parseInt(request.getParameter("cityMunCode")));
        
        
        if(linksfarmDAO.setToActiveProjectSite(Integer.parseInt(request.getParameter("cityMunCode")))){
            request.setAttribute("success", c.getCityMunDesc() + " successfully approved as Project Site!");
            request.getRequestDispatcher("provincial-field-officer-linksfarm-select-project-sites.jsp").forward(request, response);
        }else{
            request.setAttribute("errMessage", "Error in approving "+c.getCityMunDesc()+" as Project Site!");
            request.getRequestDispatcher("provincial-field-officer-linksfarm-select-project-sites.jsp").forward(request, response);
        }
        
    }

    

}
