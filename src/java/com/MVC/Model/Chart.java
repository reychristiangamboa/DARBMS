/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import be.ceau.chart.BarChart;
import be.ceau.chart.LineChart;
import be.ceau.chart.PieChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.data.LineData;
import be.ceau.chart.data.PieData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.dataset.PieDataset;
import be.ceau.chart.options.BarOptions;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.CAPDEVDAO;
import java.util.ArrayList;
import com.MVC.DAO.CropDAO;
import com.MVC.Model.Crop;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Rey Christian
 */
public class Chart {
    
    Locale pinoy = new Locale("fil", "PH");
    NumberFormat currency = NumberFormat.getCurrencyInstance(pinoy);

    public String getBarChart() {

        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(4.3);
        doubleFigures.add(4.6);
        doubleFigures.add(6.9);

        ArrayList<Double> doubleFigures2 = new ArrayList();
        doubleFigures2.add(9.4);
        doubleFigures2.add(8.1);
        doubleFigures2.add(5.5);

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Sample 1");
        stringLabels.add("Sample 2");
        stringLabels.add("Sample 3");

        BarDataset dataset = new BarDataset();
        dataset.setLabel("DATASET");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
        }
        dataset.setBorderWidth(2);

        BarDataset dataset2 = new BarDataset();
        dataset2.setLabel("CHOCOLATE");
        for (int i = 0; i < doubleFigures2.size(); i++) {
            dataset2.addData(doubleFigures2.get(i));
            dataset2.addBackgroundColor(Color.CHOCOLATE);
        }
        dataset2.setBorderWidth(2);

        BarData data = new BarData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
        data.addDataset(dataset2);

        return new BarChart(data).toJson();
    }
    
    public String getTotalYearBarChart(ArrayList<APCPRequest> requests) {
        
        ARBODAO arboDAO = new ARBODAO();
        BarData data = new BarData();
        for(APCPRequest req : requests){
            ARBO arbo = arboDAO.getARBOByID(req.getArboID());
            BarDataset dataset = new BarDataset();
            dataset.setLabel(arbo.getArboName());
            dataset.setData(req.getTotalReleaseAmount());
            dataset.addBackgroundColor(Color.random());
            dataset.setBorderWidth(2);
            data.addDataset(dataset);
        }
        
        BarOptions options = new BarOptions();
        options.setResponsive(true);
        
        return new BarChart(data, options).toJson();
    }

    public String getCropHistory(ArrayList<Crop> crops) {
        
        ArrayList<String> dates = new ArrayList();

        long l = System.currentTimeMillis();
        Date d = new Date(l);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int year = calendar.get(Calendar.YEAR);

        String lastYear = Integer.toString(year - 1);
        String thisYear = Integer.toString(year);
        String nextYear = Integer.toString(year + 1);

        int month = calendar.get(Calendar.MONTH);
        CropDAO cDAO = new CropDAO();

        if (month >= 7) { // if current month is JULY onwards
            dates.add(thisYear + "-07-31");
            dates.add(thisYear + "-08-31");
            dates.add(thisYear + "-09-30");
            dates.add(thisYear + "-10-31");
            dates.add(thisYear + "-11-30");
            dates.add(thisYear + "-12-31");
            dates.add(nextYear + "-01-31");
            dates.add(nextYear + "-02-28");
            dates.add(nextYear + "-03-31");
            dates.add(nextYear + "-04-30");
            dates.add(nextYear + "-05-31");
            dates.add(nextYear + "-06-30");
        } else {
            dates.add(lastYear + "-07-31");
            dates.add(lastYear + "-08-31");
            dates.add(lastYear + "-09-30");
            dates.add(lastYear + "-10-31");
            dates.add(lastYear + "-11-30");
            dates.add(lastYear + "-12-31");
            dates.add(thisYear + "-01-31");
            dates.add(thisYear + "-02-28");
            dates.add(thisYear + "-03-31");
            dates.add(thisYear + "-04-30");
            dates.add(thisYear + "-05-31");
            dates.add(thisYear + "-06-30");
        }

        LineData data = new LineData();

        for (Crop arbC : crops) {

            LineDataset dataset = new LineDataset();
            dataset.setBorderColor(Color.random());
            dataset.setBackgroundColor(Color.TRANSPARENT);
            dataset.setLabel(arbC.getCropTypeDesc());
            for (String date : dates) {
                dataset.addData(cDAO.getCountOfCropsByMonth(arbC, date));
            }
            data.addDataset(dataset);

        }
        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("July");
        stringLabels.add("August");
        stringLabels.add("September");
        stringLabels.add("October");
        stringLabels.add("November");
        stringLabels.add("December");
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");
        stringLabels.add("April");
        stringLabels.add("May");
        stringLabels.add("June");

        for (String label : stringLabels) {
            data.addLabel(label);
        }

        return new LineChart(data).toJson();
    }

    public String getAPCPRating(ArrayList<Evaluation> apcpEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("APCP Rating");
        
        Calendar cal = Calendar.getInstance();

        for (Evaluation e : apcpEvaluations) {
            
            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
    }
    
    public String getCAPDEVRating(ArrayList<Evaluation> capdevEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("CAPDEV Rating");
        
        Calendar cal = Calendar.getInstance();

        for (Evaluation e : capdevEvaluations) {
            
            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
    }
    
    public String getARBRating(ArrayList<Evaluation> arbEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("ARB Rating");
        
        Calendar cal = Calendar.getInstance();

        for (Evaluation e : arbEvaluations) {
            
            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
    }

    public String getLineChart() {
        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(5.3);
        doubleFigures.add(4.6);
        doubleFigures.add(6.9);

        ArrayList<Double> doubleFigures2 = new ArrayList();
        doubleFigures2.add(9.4);
        doubleFigures2.add(8.1);
        doubleFigures2.add(3.1);
        doubleFigures2.add(3.1);

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");
        stringLabels.add("June");

        LineDataset dataset = new LineDataset();
        dataset.setLabel("DATASET");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addPointBackgroundColor(Color.LIGHT_BLUE);
            dataset.setBorderColor(Color.LIGHT_BLUE);
        }
        dataset.setBorderWidth(2);

        LineDataset dataset2 = new LineDataset();
        dataset2.setLabel("CHOCOLATE");
        for (int i = 0; i < doubleFigures2.size(); i++) {
            dataset2.addData(doubleFigures2.get(i));
            dataset2.addPointBackgroundColor(Color.CHOCOLATE);
            dataset2.setBorderColor(Color.CHOCOLATE);
        }
        dataset2.setBorderWidth(2);

        LineData data = new LineData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
        data.addDataset(dataset2);

        return new LineChart(data).toJson();
    }

    public String getPieChart() {
        ArrayList<Integer> doubleFigures = new ArrayList();
        doubleFigures.add(40);
        doubleFigures.add(30);
        doubleFigures.add(30);

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");

        PieDataset dataset = new PieDataset();
        dataset.setLabel("DATASET");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.random());
        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getPieChartGender(ArrayList<ARB> arbGender) {

        ArrayList<ARB> dataMale = new ArrayList();
        ArrayList<ARB> dataFemale = new ArrayList();

        for (ARB arb : arbGender) {
            if (arb.getGender().equalsIgnoreCase("M")) {
                dataMale.add(arb);
            } else if (arb.getGender().equalsIgnoreCase("F")) {
                dataFemale.add(arb);
            }
        }
        ArrayList<Integer> doubleFigures = new ArrayList();
        doubleFigures.add(dataMale.size());
        doubleFigures.add(dataFemale.size());

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Male");
        stringLabels.add("Female");

        PieDataset dataset = new PieDataset();
        dataset.setLabel("DATASET");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.AQUA);
            dataset.addBackgroundColor(Color.PINK);

        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getPieChartPastDue(ArrayList<APCPRequest> unsettledProvincialRequests) {
        CAPDEVDAO dao = new CAPDEVDAO();
        APCPRequestDAO dao2 = new APCPRequestDAO();
        ArrayList<PastDueAccount> reasons = dao.getAllPastDueReasons();
        ArrayList<String> stringLabels = new ArrayList();
        PieData data = new PieData();
        PieDataset datasetReasons = new PieDataset();

        for (PastDueAccount reason : reasons) {
            int count = 0;
            stringLabels.add(reason.getReasonPastDueDesc());
            for (APCPRequest r : unsettledProvincialRequests) {
                ArrayList<PastDueAccount> unsettled = dao2.getAllUnsettledPastDueAccountsByRequest(r.getRequestID());
                for (PastDueAccount pda : unsettled) {
                    if (pda.getReasonPastDue() == reason.getReasonPastDue()) {
                        count++;
                    }
                }
            }
            datasetReasons.addData(count);
            datasetReasons.addBackgroundColor(Color.random());
            datasetReasons.setBorderWidth(2);
        }

        data.addDataset(datasetReasons);
        for (String l : stringLabels) {
            data.addLabel(l);
        }

        return new PieChart(data).toJson();
    }

    public String getPieChartDisbursement(ArrayList<ARB> arbList) {

        APCPRequestDAO dao2 = new APCPRequestDAO();
        ArrayList<String> stringLabels = new ArrayList();
        PieData data = new PieData();
        PieDataset datasetDisbursements = new PieDataset();

        for (ARB arb : arbList) {
            double amount = 0;
            stringLabels.add(arb.getFLName());
            ArrayList<Disbursement> disbursements = dao2.getAllDisbursementsByARB(arb.getArbID());

            for (Disbursement d : disbursements) {
                amount += d.getDisbursedAmount();
            }

            datasetDisbursements.addData(amount);
            datasetDisbursements.addBackgroundColor(Color.random());
            datasetDisbursements.setBorderWidth(2);

        }

        data.addDataset(datasetDisbursements);
        for (String l : stringLabels) {
            data.addLabel(l);
        }

        return new PieChart(data).toJson();
    }

    public String getBarChartEducation(ArrayList<ARB> arbEducChart) {
        int noGrade = 0;
        int primary = 0;
        int intermediate = 0;
        int elementaryGrad = 0;
        int hsLevel = 0;
        int hsGrad = 0;
        int collegeLevel = 0;
        int collegeGrad = 0;
        int gradsStud = 0;
        int vocational = 0;

        for (int i = 0; i < arbEducChart.size(); i++) {
            if (arbEducChart.get(i).getEducationLevel() == 0) {
                noGrade++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 1) {
                primary++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 2) {
                intermediate++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 3) {
                elementaryGrad++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 4) {
                hsLevel++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 5) {
                hsGrad++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 6) {
                collegeLevel++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 7) {
                collegeGrad++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 8) {
                gradsStud++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 9) {
                vocational++;
            }
        }
        ArrayList<Integer> doubleFigures = new ArrayList();
        doubleFigures.add(noGrade);
        doubleFigures.add(primary);
        doubleFigures.add(intermediate);
        doubleFigures.add(elementaryGrad);
        doubleFigures.add(hsLevel);
        doubleFigures.add(hsGrad);
        doubleFigures.add(collegeLevel);
        doubleFigures.add(collegeGrad);
        doubleFigures.add(gradsStud);
        doubleFigures.add(vocational);

        ArrayList<Integer> primaryEduc = new ArrayList();
        primaryEduc.add(primary);

        ArrayList<Integer> intermediateEduc = new ArrayList();
        intermediateEduc.add(intermediate);

        ArrayList<Integer> elementaryGradEduc = new ArrayList();
        elementaryGradEduc.add(elementaryGrad);

        ArrayList<Integer> hsLevelEduc = new ArrayList();
        hsLevelEduc.add(hsLevel);

        ArrayList<Integer> hsGradEduc = new ArrayList();
        hsGradEduc.add(hsGrad);

        ArrayList<Integer> collegeLevelEduc = new ArrayList();
        collegeLevelEduc.add(collegeLevel);

        ArrayList<Integer> collegeGradEduc = new ArrayList();
        collegeGradEduc.add(collegeGrad);

        ArrayList<Integer> gradsStudEduc = new ArrayList();
        gradsStudEduc.add(gradsStud);

        ArrayList<Integer> vocationalEduc = new ArrayList();
        vocationalEduc.add(vocational);

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Educational Level");

        BarDataset dataset = new BarDataset();
        dataset.setLabel("No Formal Level");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
        }
        dataset.setBorderWidth(2);

        BarDataset datasetPrimaryEduc = new BarDataset();
        datasetPrimaryEduc.setLabel("Primary");
        for (int i = 0; i < primaryEduc.size(); i++) {
            datasetPrimaryEduc.addData(primaryEduc.get(i));
            datasetPrimaryEduc.addBackgroundColor(Color.RED);
        }
        datasetPrimaryEduc.setBorderWidth(2);

        BarDataset datasetsIntermediateEduc = new BarDataset();
        datasetsIntermediateEduc.setLabel("Intermediate");
        for (int i = 0; i < intermediateEduc.size(); i++) {
            datasetsIntermediateEduc.addData(intermediateEduc.get(i));
            datasetsIntermediateEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsIntermediateEduc.setBorderWidth(2);

        BarDataset datasetsElementaryGradEduc = new BarDataset();
        datasetsElementaryGradEduc.setLabel("Elementary Graduate");
        for (int i = 0; i < elementaryGradEduc.size(); i++) {
            datasetsElementaryGradEduc.addData(elementaryGradEduc.get(i));
            datasetsElementaryGradEduc.addBackgroundColor(Color.GREEN);
        }
        datasetsElementaryGradEduc.setBorderWidth(2);

        BarDataset datasetsHsLevelEduc = new BarDataset();
        datasetsHsLevelEduc.setLabel("High School Level");
        for (int i = 0; i < hsLevelEduc.size(); i++) {
            datasetsHsLevelEduc.addData(hsLevelEduc.get(i));
            datasetsHsLevelEduc.addBackgroundColor(Color.AZURE);
        }
        datasetsHsLevelEduc.setBorderWidth(2);

        BarDataset datasetsHsGradEduc = new BarDataset();
        datasetsHsGradEduc.setLabel("High School Graduate");
        for (int i = 0; i < hsGradEduc.size(); i++) {
            datasetsHsGradEduc.addData(hsGradEduc.get(i));
            datasetsHsGradEduc.addBackgroundColor(Color.BLACK);
        }
        datasetsHsGradEduc.setBorderWidth(2);

        BarDataset datasetsCollegeLevelEduc = new BarDataset();
        datasetsCollegeLevelEduc.setLabel("College Level");
        for (int i = 0; i < collegeLevelEduc.size(); i++) {
            datasetsCollegeLevelEduc.addData(collegeLevelEduc.get(i));
            datasetsCollegeLevelEduc.addBackgroundColor(Color.BROWN);
        }
        datasetsCollegeLevelEduc.setBorderWidth(2);

        BarDataset datasetsCollegeGradEduc = new BarDataset();
        datasetsCollegeGradEduc.setLabel("College Graduate");
        for (int i = 0; i < collegeGradEduc.size(); i++) {
            datasetsCollegeGradEduc.addData(collegeGradEduc.get(i));
            datasetsCollegeGradEduc.addBackgroundColor(Color.CRIMSON);
        }
        datasetsCollegeGradEduc.setBorderWidth(2);

        BarDataset datasetsGradsStudEduc = new BarDataset();
        datasetsGradsStudEduc.setLabel("Graduate Studies");
        for (int i = 0; i < gradsStudEduc.size(); i++) {
            datasetsGradsStudEduc.addData(gradsStudEduc.get(i));
            datasetsGradsStudEduc.addBackgroundColor(Color.GOLD);
        }
        datasetsGradsStudEduc.setBorderWidth(2);

        BarDataset datasetsVocationalEduc = new BarDataset();
        datasetsVocationalEduc.setLabel("Vocational");
        for (int i = 0; i < vocationalEduc.size(); i++) {
            datasetsVocationalEduc.addData(vocationalEduc.get(i));
            datasetsVocationalEduc.addBackgroundColor(Color.DARK_KHAKI);
        }
        datasetsVocationalEduc.setBorderWidth(2);

        BarData data = new BarData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
        data.addDataset(datasetPrimaryEduc);
        data.addDataset(datasetsIntermediateEduc);
        data.addDataset(datasetsElementaryGradEduc);
        data.addDataset(datasetsHsLevelEduc);
        data.addDataset(datasetsHsGradEduc);
        data.addDataset(datasetsCollegeLevelEduc);
        data.addDataset(datasetsCollegeGradEduc);
        data.addDataset(datasetsGradsStudEduc);
        data.addDataset(datasetsVocationalEduc);

        return new BarChart(data).toJson();
    }
    
    

}