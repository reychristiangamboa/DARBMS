/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.CAPDEVDAO;
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
public class FilterCAPDEVProposals extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String[] provOfficeCodesStr = request.getParameterValues("valajax");
        
        System.out.println(provOfficeCodesStr.length);
        
        ArrayList<Integer> provOfficeCode = new ArrayList();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();

        for(String code : provOfficeCodesStr){
            provOfficeCode.add(Integer.parseInt(code));
        }
        
        
        
        
        
    }

}
