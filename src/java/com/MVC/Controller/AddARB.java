/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.Model.ARB;
import com.MVC.Model.ARBO;
import com.MVC.Model.Crop;
import com.MVC.Model.Dependent;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rey Christian
 */
public class AddARB extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("manual") != null) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println(request.getParameter("memberSince"));

            ARBDAO arbDAO = new ARBDAO();
            ARBODAO arboDAO = new ARBODAO();

            int arboID = Integer.parseInt(request.getParameter("arboID"));
            ARBO arbo = arboDAO.getARBOByID(arboID);

            ARB arb = new ARB();

            arb.setArboID(arboID);

            if (request.getParameter("arboRep").equals("Yes")) {
                arb.setArboRepresentative(1);
            } else if (request.getParameter("arboRep").equals("No")) {
                arb.setArboRepresentative(0);
            }

            arb.setFirstName(request.getParameter("firstName"));
            arb.setMiddleName(request.getParameter("middleName"));
            arb.setLastName(request.getParameter("lastName"));

            java.sql.Date memberSince = null;

            try {
                java.util.Date parsedMemberDate = sdf.parse(request.getParameter("memberSince"));
                memberSince = new java.sql.Date(parsedMemberDate.getTime());
            } catch (ParseException ex) {
                Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
            }

            arb.setMemberSince(memberSince);

            arb.setArbUnitNumStreet(request.getParameter("arbUnitNumStreet"));
            arb.setBrgyCode(Integer.parseInt(request.getParameter("barangay")));
            arb.setCityMunCode(Integer.parseInt(request.getParameter("arboCityMun")));
            arb.setProvCode(Integer.parseInt(request.getParameter("arboProvince")));
            arb.setRegCode(Integer.parseInt(request.getParameter("arbRegion")));

            arb.setGender(request.getParameter("gender"));
            arb.setEducationLevel(Integer.parseInt(request.getParameter("educationalLevel")));
            arb.setLandArea(Double.parseDouble(request.getParameter("landArea")));

            if (arbDAO.checkIfARBExists(arb)) {
                request.setAttribute("errMessage", "ARB already exists. Try again.");
                request.setAttribute("arbo", arbo);
                request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
            } else {
                int arbID = arbDAO.addARB(arb);

                String[] crops = request.getParameterValues("crops[]");
                String[] startDateArr = request.getParameterValues("startDate[]");
                String[] endDateArr = request.getParameterValues("endDate[]");

                ArrayList<Crop> cropList = new ArrayList();

                for (int i = 0; i < crops.length; i++) {
                    Crop c = new Crop();
                    c.setCropType(Integer.parseInt(crops[i]));

                    java.sql.Date startDate = null;

                    try {
                        java.util.Date parsedStartDate = sdf.parse(startDateArr[i]);
                        startDate = new java.sql.Date(parsedStartDate.getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    c.setStartDate(startDate);

                    if (endDateArr[i] != null) {
                        java.sql.Date endDate = null;

                        try {
                            java.util.Date parsedEndDate = sdf.parse(endDateArr[i]);
                            endDate = new java.sql.Date(parsedEndDate.getTime());
                        } catch (ParseException ex) {
                            Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        c.setEndDate(endDate);
                    }

                    cropList.add(c);
                }

                String[] names = request.getParameterValues("dependentName[]");
                String[] birthdays = request.getParameterValues("dependentBirthday[]");
                String[] el = request.getParameterValues("dependentEL[]");
                String[] re = request.getParameterValues("dependentR[]");
                System.out.println(re[0]);
                ArrayList<Dependent> dependentList = new ArrayList();
                
                for (int x = 0; x < names.length; x++) {

                    Dependent d = new Dependent();
                    d.setName(names[x]);
                    d.setEducationLevel(Integer.parseInt(el[x]));
                    d.setRelationshipType(Integer.parseInt(re[x]));

                    java.sql.Date birthday = null;

                    try {
                        java.util.Date parsedBirthday = sdf.parse(birthdays[x]);
                        birthday = new java.sql.Date(parsedBirthday.getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(AddARB.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    d.setBirthday(birthday);

                    dependentList.add(d);

                }

                if (arbDAO.addCrops(arbID, cropList)) {
                    if (arbDAO.addDependents(arbID, dependentList)) {
                        request.setAttribute("success", "ARB added!");
                        request.setAttribute("arbo", arbo);
                        request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errMessage", "Error in adding ARB. Try again.");
                        request.setAttribute("arbo", arbo);
                        request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errMessage", "Error in adding ARB. Try again.");
                    request.setAttribute("arbo", arbo);
                    request.getRequestDispatcher("provincial-field-officer-add-arb.jsp").forward(request, response);
                }
            }

        }

    }
}
