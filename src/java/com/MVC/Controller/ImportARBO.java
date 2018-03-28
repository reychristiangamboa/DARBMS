/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Controller;

import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.AddressDAO;
import com.MVC.Model.ARBO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Rey Christian
 */
public class ImportARBO extends BaseServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("import") != null) {
            
            HttpSession session = request.getSession();
            
            AddressDAO addressDAO = new AddressDAO();
            ARBODAO arboDAO = new ARBODAO();
            ArrayList<ARBO> arboList = new ArrayList();

            ArrayList dataHolder = readExcelFile(request.getParameter("file"));
            
            ArrayList cellStoreArrayList = new ArrayList();

            for (int i = 1; i < dataHolder.size(); i++) {
                
                cellStoreArrayList = (ArrayList) dataHolder.get(i);
                ARBO arbo = new ARBO();
                arbo.setArboName(cellStoreArrayList.get(0).toString()); // GET ARBO NAME
                
                
                int regCode = addressDAO.getRegCode(cellStoreArrayList.get(3).toString()); // GET REGION, TURN INTO INT
                arbo.setArboRegion(regCode);
                int provCode = addressDAO.getProvCode(cellStoreArrayList.get(2).toString(),regCode); // GET PROVINCE, TURN INTO INT
                arbo.setArboProvince(provCode);
                int cityMunCode = addressDAO.getCityMunCode(cellStoreArrayList.get(1).toString(),provCode,regCode); // GET CITY, TURN INTO INT
                arbo.setArboCityMun(cityMunCode);
                
                if(cellStoreArrayList.get(4).toString().equalsIgnoreCase("No")){
                    arbo.setAPCPQualified(0);
                }else if(cellStoreArrayList.get(4).toString().equalsIgnoreCase("Yes")){
                    arbo.setAPCPQualified(1);
                }
                
                arbo.setProvOfficeCode((Integer)session.getAttribute("provOfficeCode"));
                
                
                
                arboList.add(arbo);
            }
            
            if (arboDAO.addARBOExcel(arboList)) {
                request.setAttribute("success", "ARBOs imported!");
                request.getRequestDispatcher("provincial-field-officer-add-arbo.jsp").forward(request, response);
            } else {
                request.setAttribute("errMessage", "Error in importing ARBO. Try again.");
                request.getRequestDispatcher("provincial-field-officer-add-arbo.jsp").forward(request, response);
            }
        }
    }

    public static ArrayList readExcelFile(String file) {
        ArrayList cellArrayListHolder = new ArrayList();
        try {
            OPCPackage pkg = OPCPackage.open(file);
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator rowIter = sheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow row = (XSSFRow) rowIter.next();
                Iterator cellIter = row.cellIterator();

                ArrayList cellStoreArrayList = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIter.next();
                    cellStoreArrayList.add(cell);
                }
                cellArrayListHolder.add(cellStoreArrayList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellArrayListHolder;
    }
}
