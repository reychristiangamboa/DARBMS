/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.LINKSFARMDAO;
import com.MVC.DAO.UserDAO;
import com.MVC.Model.ARB;
import com.MVC.Model.Cluster;
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
 * @author Z40
 */
public class ClusterARB extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");

        String clusterName = request.getParameter("clusterName");
        int cityMunCode = Integer.parseInt(request.getParameter("cityMunCode"));

        String[] arbID = request.getParameterValues("IDs");
        ArrayList<ARB> clusteredARBs = new ArrayList();
        ARBDAO dao = new ARBDAO();
        LINKSFARMDAO linksFarmDAO = new LINKSFARMDAO();
        Cluster c = new Cluster();

        int clusterID = linksFarmDAO.addCluster(clusterName, cityMunCode);

        for (String id : arbID) {
            ARB arb = dao.getARBByID(Integer.parseInt(id));
            clusteredARBs.add(arb);
        }

        if (linksFarmDAO.setClusterID(clusteredARBs, clusterID)) {
            c = linksFarmDAO.getClusterByID(clusterID);
            request.setAttribute("cluster", c);
            request.getRequestDispatcher("cluster-profile.jsp").forward(request, response);
        }else{
            
        }

    }

}
