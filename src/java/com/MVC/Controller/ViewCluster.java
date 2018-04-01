/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.LINKSFARMDAO;
import com.MVC.Model.Cluster;
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
public class ViewCluster extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        LINKSFARMDAO dao = new LINKSFARMDAO();
        Cluster c = new Cluster();
        c = dao.getClusterByID(Integer.parseInt(request.getParameter("clusterID")));
        
        request.setAttribute("cluster", c);
        request.getRequestDispatcher("cluster-profile.jsp").forward(request, response);
        
    }

    

}
