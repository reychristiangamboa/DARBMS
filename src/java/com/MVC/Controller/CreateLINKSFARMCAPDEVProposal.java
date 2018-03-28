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
 * @author Christopher Jorge
 */
public class CreateLINKSFARMCAPDEVProposal extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        LINKSFARMDAO dao = new LINKSFARMDAO();
        Cluster c = dao.getClusterByID(Integer.parseInt(request.getParameter("clusterID")));
        
        request.setAttribute("cluster", c);
        request.getRequestDispatcher("provincial-field-officer-create-linksfarm-capdev-proposal.jsp").forward(request, response);
    }

    

}
